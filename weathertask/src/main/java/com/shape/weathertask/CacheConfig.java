package com.shape.weathertask;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.shape.weathertask.model.WeatherKeyGenerator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
@EnableCaching
@Configuration
public class CacheConfig {
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .initialCapacity(10);
    }
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @Bean("weatherKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new WeatherKeyGenerator();
    }
}