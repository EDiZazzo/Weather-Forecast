package com.shape.weathertask.model.rest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonPropertyOrder({"list"})
@Data
@Getter
public class OpenWeatherMapApiResponse {

    public ArrayList<List> list;
    public City city;

    public OpenWeatherMapApiResponse() {
    }

    @Data
    public static class List{

        public Main main;

        @JsonAlias("dt_txt")
        public String timeStamp;
    }

    @Data
    public static class Main {
        public double temp;
    }

    @Data
    public static class City {
        public String id;
        public String name;
    }
}
