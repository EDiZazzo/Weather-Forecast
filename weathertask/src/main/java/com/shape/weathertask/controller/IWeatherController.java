package com.shape.weathertask.controller;

import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.WeatherData;
import com.shape.weathertask.model.WeatherSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org .springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Weather", description = "Weather Api")
@Validated
public interface IWeatherController {
    @Operation(
            summary = "Get weather summary for cities",
            operationId = "getWeatherSummary",
            description = "Gets a list of cities where the temperature will be above a certain temperature the next day.",
            tags = {"Weather"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "429", description = "The server cannot accept more calls"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/summary")
    ResponseEntity<WeatherSummary> getWeatherSummary(
            @Parameter(in = QUERY, name = "unit", description = "The unit of temperature to use.") TemperatureUnit unit,
            @Parameter(in = QUERY, name = "temperature", description = "The minimum temperature to filter by.", required = true) int temperature,
            @Parameter(in = QUERY, name = "cities", description = "The IDs of the cities to get weather data for.", required = true) String[] cities);

    @Operation(
            summary = "Get weather data for a specific city for the next 5 Days",
            operationId = "getWeatherSummary",
            description = "Gets the weather data for a specific city.",
            tags = {"Weather"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "City not found"),
                    @ApiResponse(responseCode = "429", description = "The server cannot accept more calls"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/cities/{cityId}")
    ResponseEntity<WeatherData> getWeatherData(
            @Parameter(in = PATH, description = "The ID of the city to get weather data for.", required = true) @PathVariable String cityId,
            @Parameter(in = QUERY, name = "unit", description = "The unit of temperature to use.", hidden = true) TemperatureUnit unit);
}