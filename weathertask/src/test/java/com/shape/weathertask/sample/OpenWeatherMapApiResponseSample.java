package com.shape.weathertask.sample;

import com.shape.weathertask.model.rest.OpenWeatherMapApiResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OpenWeatherMapApiResponseSample {

    public static OpenWeatherMapApiResponse createSampleWarmResponse() {
        OpenWeatherMapApiResponse response = new OpenWeatherMapApiResponse();
        response.setCity(new OpenWeatherMapApiResponse.City());

        response.city.id = "1";
        response.city.name = "Beautiful City";

        ArrayList<OpenWeatherMapApiResponse.List> forecastList = new ArrayList<>();
        forecastList.add(createSampleList(20.0, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        forecastList.add(createSampleList(30.0, LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        forecastList.add(createSampleList(12.0, LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        forecastList.add(createSampleList(42.0, LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        response.setList(forecastList);

        return response;
    }

    public static OpenWeatherMapApiResponse createSampleColdResponse() {
        OpenWeatherMapApiResponse response = new OpenWeatherMapApiResponse();
        response.setCity(new OpenWeatherMapApiResponse.City());

        response.city.id = "2";
        response.city.name = "Very Cold City";

        ArrayList<OpenWeatherMapApiResponse.List> forecastList = new ArrayList<>();
        forecastList.add(createSampleList(-20.0, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        forecastList.add(createSampleList(-20.0, LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        forecastList.add(createSampleList(-30.0, LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        forecastList.add(createSampleList(-40.0, LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        response.setList(forecastList);

        return response;
    }

    private static OpenWeatherMapApiResponse.List createSampleList(double temperature, String timestamp) {
        OpenWeatherMapApiResponse.List list = new OpenWeatherMapApiResponse.List();
        list.setMain(new OpenWeatherMapApiResponse.Main());
        list.main.temp = temperature;
        list.setTimeStamp(timestamp);
        return list;
    }
}
