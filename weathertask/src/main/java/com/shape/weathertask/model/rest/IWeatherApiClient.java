package com.shape.weathertask.model.rest;

import com.shape.weathertask.model.TemperatureUnit;

public interface IWeatherApiClient {

    OpenWeatherMapApiResponse getOpenWeatherMapApiForecast(String city, TemperatureUnit unit);
}
