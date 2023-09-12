package com.shape.weathertask.service;

import com.shape.weathertask.model.Forecast;
import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.WeatherData;
import com.shape.weathertask.model.WeatherSummary;
import com.shape.weathertask.model.rest.OpenWeatherMapApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WeatherService implements IWeatherService {

    private final ICacheService cacheService;

    @Autowired
    public WeatherService(ICacheService cacheService) {
        this.cacheService = cacheService;
    }


    // Fetch weather summaries for favorite cities based on unit, temperature, and cities
    @SneakyThrows
    public WeatherSummary getWeatherSummary(TemperatureUnit unit, int temperature, List<String> cities){

        WeatherSummary citiesResponse = new WeatherSummary();
        for (String city : cities) {
            if (city.isEmpty()) {
                continue;
            }

            OpenWeatherMapApiResponse openWeatherMapApiResponse = getOpenWeatherMapApiResponse(city, unit);

            if(openWeatherMapApiResponse.getCity() == null){
                continue;
            }

            double maxTemperatureForecastNextDay = getWeatherData(openWeatherMapApiResponse).getForecastList().stream()
                    .filter(f -> f.getDateTime().getDayOfYear() == (LocalDateTime.now().getDayOfYear() + 1))
                    .max(Comparator.comparingDouble(Forecast::getTemperature)).get().getTemperature();

            if (maxTemperatureForecastNextDay > temperature) {
                citiesResponse.addCity(openWeatherMapApiResponse.getCity());

            }
        }
        return citiesResponse;
    }

    // Fetch weather data for a specific city based on city ID
    @SneakyThrows
    public WeatherData getWeatherDataNextDays(String cityId, TemperatureUnit unit) {

        if (cityId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city cannot be empty.");
        }

        OpenWeatherMapApiResponse openWeatherMapApiResponse = getOpenWeatherMapApiResponse(cityId, unit);

        if(openWeatherMapApiResponse.getCity() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no city with this id");
        }

        WeatherData weatherData = getWeatherData(openWeatherMapApiResponse);

        WeatherData weatherDataNextFiveDays = new WeatherData(openWeatherMapApiResponse.getCity().getId(), openWeatherMapApiResponse.getCity().getName());

        int today = LocalDateTime.now().getDayOfYear();

        weatherDataNextFiveDays.setForecastList(weatherData.getForecastList().stream()
                .filter(f -> f.getDateTime().getDayOfYear() > today && f.getDateTime().getDayOfYear() <= (today + 5)).toList());

        return weatherDataNextFiveDays;
    }

    public OpenWeatherMapApiResponse getOpenWeatherMapApiResponse(String cityId, TemperatureUnit unit) {

        return cacheService.getOpenWeatherMapApiResponse(cityId, unit);
    }

    public WeatherData getWeatherData(OpenWeatherMapApiResponse openWeatherMapApiResponse) {

        ArrayList<OpenWeatherMapApiResponse.List> temperaturePerHourList = openWeatherMapApiResponse.getList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        WeatherData weatherData = new WeatherData(openWeatherMapApiResponse.getCity().getId(), openWeatherMapApiResponse.getCity().getName());

        for (OpenWeatherMapApiResponse.List l : temperaturePerHourList) {
            weatherData.addForecast(l.getMain().getTemp(), LocalDateTime.parse(l.getTimeStamp(), formatter));
        }

        return weatherData;
    }
}