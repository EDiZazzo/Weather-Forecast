package com.shape.weathertask.controller;

import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.WeatherData;
import com.shape.weathertask.model.WeatherSummary;
import com.shape.weathertask.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/weather")
public class WeatherController implements IWeatherController {
    private final IWeatherService weatherService;

    @Autowired
    public WeatherController(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public ResponseEntity<WeatherSummary> getWeatherSummary(TemperatureUnit unit, int temperature, String[] cities) {

        WeatherSummary weatherSummary = weatherService.getWeatherSummary(unit, temperature, Arrays.stream(cities).toList());
        if(weatherSummary.getCities().isEmpty()){
            return new ResponseEntity<>(weatherSummary, NO_CONTENT);
        }
        return new ResponseEntity<>(weatherSummary, OK);
    }

    @Override
    public ResponseEntity<WeatherData> getWeatherData(String cityId, TemperatureUnit unit) {
        //Default Value unit = CELSIUS
        if(unit == null){
           unit = TemperatureUnit.CELSIUS;
        }
        WeatherData weatherData = weatherService.getWeatherDataNextDays(cityId, unit);
        return new ResponseEntity<>(weatherData, OK);
    }
}
