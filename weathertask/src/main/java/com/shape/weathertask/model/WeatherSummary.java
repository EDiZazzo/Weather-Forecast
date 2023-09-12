package com.shape.weathertask.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.shape.weathertask.model.rest.OpenWeatherMapApiResponse.City;

@Getter
@Setter
public class WeatherSummary {
    private List<City> cities;

    public WeatherSummary(){
        this.cities = new ArrayList<>();
    }

    public void addCity(City city){
        this.cities.add(city);
    }
}