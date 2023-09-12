package com.shape.weathertask;

import com.shape.weathertask.model.repository.ApiUsageRepository;
import com.shape.weathertask.model.rest.IWeatherApiClient;
import com.shape.weathertask.model.rest.WeatherApiClient;
import com.shape.weathertask.service.CacheService;
import com.shape.weathertask.service.ICacheService;
import com.shape.weathertask.service.IWeatherService;
import com.shape.weathertask.service.WeatherService;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {
    @Bean
    public IWeatherService weatherService(ICacheService cacheService){
        return new WeatherService(cacheService);
    }

    @Bean
    public ICacheService cacheService(IWeatherApiClient weatherApiClient, CacheManager cacheManager) {
        return new CacheService(weatherApiClient, cacheManager);
    }

    @Bean
    public IWeatherApiClient weatherApiClient(ApiUsageRepository apiUsageRepository){

        return new WeatherApiClient(restTemplate(), apiUsageRepository);
    }

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
