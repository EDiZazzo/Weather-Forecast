package com.shape.weathertask.service;

import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.WeatherData;
import com.shape.weathertask.model.WeatherSummary;

import java.util.List;

public interface IWeatherService {

    WeatherSummary getWeatherSummary(TemperatureUnit unit, int temperature, List<String> cities);

    WeatherData getWeatherDataNextDays(String cityId, TemperatureUnit unit);
}
