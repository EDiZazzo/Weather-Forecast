package com.shape.weathertask.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class WeatherKeyGenerator implements KeyGenerator {

    @Override
    public @NotNull Object generate(Object target, Method method, Object... params) {
        String city = params[0].toString();
        String unit = params[1].toString();
        return city + "-" + unit + "-" + LocalDateTime.now().getDayOfYear();
    }
}