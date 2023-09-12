package com.shape.weathertask.model.rest;

import com.shape.weathertask.model.repository.ApiUsage;
import com.shape.weathertask.model.repository.ApiUsageRepository;
import com.shape.weathertask.model.TemperatureUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Component
public class WeatherApiClient implements IWeatherApiClient {

    @Value("${weather-api-path}")
    public String WEATHER_PATH;

    @Value("${open-weather-map}")
    private String apiKey;

    @Value("${max-api-number-of-calls}")
    private int maxApiNumberOfCalls;
    private final RestTemplate restTemplate;
    private final ApiUsageRepository apiUsageRepository;

    @Autowired
    public WeatherApiClient(RestTemplate restTemplate, ApiUsageRepository apiUsageRepository) {
        this.restTemplate = restTemplate;
        this.apiUsageRepository = apiUsageRepository;
    }

    public OpenWeatherMapApiResponse getOpenWeatherMapApiForecast(String city, TemperatureUnit unit) {

        if (isDailyLimitExceeded()) {
            throw new DailyApiLimitExceededException("Today Api cannot be called anymore");
        }

        String unitRequest = "";
        switch (unit) {
            case CELSIUS -> {
                unitRequest = "metric";
            }
            case FAHRENHEIT -> {
                unitRequest = "imperial";
            }
        }
        String url = UriComponentsBuilder
                .fromHttpUrl(WEATHER_PATH)
                .queryParam("units", unitRequest)
                .queryParam("id", city)
                .queryParam("appid", apiKey)
                .build().toString();
        updateApiUsage();

        try {
            return restTemplate.getForObject(url, OpenWeatherMapApiResponse.class);
        } catch (RestClientException e) {
            return new OpenWeatherMapApiResponse();
        }
    }

    private boolean isDailyLimitExceeded() {
        LocalDate currentDate = LocalDate.now();
        ApiUsage usage = apiUsageRepository.findByApiNameAndDate("WeatherApi", currentDate);

        return usage != null && usage.getCount() >= maxApiNumberOfCalls;
    }

    private void updateApiUsage() {
        LocalDate currentDate = LocalDate.now();

        ApiUsage usage = apiUsageRepository.findByApiNameAndDate("WeatherApi", currentDate);

        if (usage == null) {
            usage = new ApiUsage();
            usage.setApiName("WeatherApi");
            usage.setDate(currentDate);
            usage.setCount(1L);
        } else {
            usage.setCount(usage.getCount() + 1);
        }

        apiUsageRepository.save(usage);
    }
}
