package com.shape.weathertask.service;

import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.rest.IWeatherApiClient;
import com.shape.weathertask.model.rest.OpenWeatherMapApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService implements ICacheService{

    private final IWeatherApiClient weatherApiClient;

    private final CacheManager cacheManager;

    @Autowired
    public CacheService(IWeatherApiClient weatherApiClient, CacheManager cacheManager) {
        this.weatherApiClient = weatherApiClient;
        this.cacheManager = cacheManager;
    }


    @Cacheable(value = "openWeatherMapApiResponse", keyGenerator = "weatherKeyGenerator")
    public OpenWeatherMapApiResponse getOpenWeatherMapApiResponse(String city, TemperatureUnit unit) {
        return weatherApiClient.getOpenWeatherMapApiForecast(city, unit);
    }
}
