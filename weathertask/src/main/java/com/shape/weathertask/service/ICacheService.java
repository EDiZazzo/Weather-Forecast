package com.shape.weathertask.service;

import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.rest.OpenWeatherMapApiResponse;

public interface ICacheService {

    OpenWeatherMapApiResponse getOpenWeatherMapApiResponse(String city, TemperatureUnit unit);
}
