package com.shape.weathertask.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Forecast{

    private final double Temperature;
    private final LocalDateTime DateTime;

    public Forecast(double Temperature, LocalDateTime DateTime){
        this.DateTime = DateTime;
        this.Temperature = Temperature;
    }
}
