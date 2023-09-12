package com.shape.weathertask.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WeatherData {

    private String CityId;
    private String CityName;
    private List<Forecast> forecastList;

    public WeatherData(String CityId, String CityName) {
        this.CityId = CityId;
        this.CityName = CityName;
        this.forecastList = new ArrayList<>();
    }

    public WeatherData() {
    }

    public void addForecast(double temperature, LocalDateTime dateTime){
        this.forecastList.add(new Forecast(temperature, dateTime));
    }
}
