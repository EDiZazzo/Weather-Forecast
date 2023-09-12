package com.shape.weathertask.service;

import com.shape.weathertask.model.TemperatureUnit;
import com.shape.weathertask.model.WeatherData;
import com.shape.weathertask.model.WeatherSummary;
import com.shape.weathertask.model.rest.OpenWeatherMapApiResponse;
import com.shape.weathertask.sample.OpenWeatherMapApiResponseSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {

    @Mock
    private ICacheService cacheService;

    private IWeatherService weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService(cacheService);
    }

    @Test
    public void testGetWeatherSummary_OneValidCity() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);

        OpenWeatherMapApiResponse sampleResponseCold = coldMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("2", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseCold);

        // Act
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        int temperature = 25;
        List<String> cities = Arrays.asList("1", "2");
        WeatherSummary summary = weatherService.getWeatherSummary(unit, temperature, cities);

        // Assert
        assertNotNull(summary);
        assertThat(summary.getCities().get(0).getId()).isEqualTo("1");
        assertThat(summary.getCities().get(0).getName()).isEqualTo("Beautiful City");
    }

    @Test
    public void testGetWeatherSummary_BothValidCity() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);

        OpenWeatherMapApiResponse sampleResponseCold = coldMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("2", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseCold);

        // Act
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        int temperature = -125;
        List<String> cities = Arrays.asList("1", "2");
        WeatherSummary summary = weatherService.getWeatherSummary(unit, temperature, cities);

        // Assert
        assertNotNull(summary);
        assertThat(summary.getCities().get(0).getId()).isEqualTo("1");
        assertThat(summary.getCities().get(0).getName()).isEqualTo("Beautiful City");
        assertThat(summary.getCities().get(1).getId()).isEqualTo("2");
        assertThat(summary.getCities().get(1).getName()).isEqualTo("Very Cold City");
    }

    @Test
    public void testGetWeatherSummary_OneInvalidCity() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);
        when(cacheService.getOpenWeatherMapApiResponse("InvalidCity", TemperatureUnit.CELSIUS)).thenReturn(new OpenWeatherMapApiResponse());

        // Act and Assert
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        int temperature = 25;
        List<String> cities = Arrays.asList("1", "InvalidCity");
        WeatherSummary summary = weatherService.getWeatherSummary(unit, temperature, cities);

        // Assert
        assertNotNull(summary);
        assertThat(summary.getCities().get(0).getId()).isEqualTo("1");
        assertThat(summary.getCities().get(0).getName()).isEqualTo("Beautiful City");
    }

    @Test
    public void testGetWeatherSummary_OneEmptyCity() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);
        when(cacheService.getOpenWeatherMapApiResponse("", TemperatureUnit.CELSIUS)).thenReturn(new OpenWeatherMapApiResponse());

        // Act and Assert
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        int temperature = 25;
        List<String> cities = Arrays.asList("1", "");
        WeatherSummary summary = weatherService.getWeatherSummary(unit, temperature, cities);

        // Assert
        assertNotNull(summary);
        assertThat(summary.getCities().get(0).getId()).isEqualTo("1");
        assertThat(summary.getCities().get(0).getName()).isEqualTo("Beautiful City");
    }


    @Test
    public void testGetWeatherSummary_NoValidCity() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);

        OpenWeatherMapApiResponse sampleResponseCold = coldMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("2", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseCold);

        // Act
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        int temperature = 125;
        List<String> cities = Arrays.asList("1", "2");
        WeatherSummary summary = weatherService.getWeatherSummary(unit, temperature, cities);

        // Assert
        assertThat(summary.getCities()).isEmpty();
    }

    @Test
    public void testGetWeatherDataNextDays() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);

        // Act
        String cityId = "1";
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        WeatherData weatherData = weatherService.getWeatherDataNextDays(cityId, unit);

        // Assert
        assertNotNull(weatherData);
        assertThat(weatherData.getCityName()).isEqualTo("Beautiful City");
        assertThat(weatherData.getCityId()).isEqualTo("1");
        assertThat(weatherData.getForecastList().size()).isEqualTo(3);
    }

    @Test
    public void testGetWeatherDataNextDays_EmptyCity() {
        // Arrange
        OpenWeatherMapApiResponse sampleResponseWarm = warmMockedOpenWeatherMapApiResponse();
        when(cacheService.getOpenWeatherMapApiResponse("1", TemperatureUnit.CELSIUS)).thenReturn(sampleResponseWarm);

        // Act and Assert
        String cityId = "";
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        assertThrows(ResponseStatusException.class, () -> weatherService.getWeatherDataNextDays(cityId, unit));
    }

    private OpenWeatherMapApiResponse warmMockedOpenWeatherMapApiResponse() {
        return OpenWeatherMapApiResponseSample.createSampleWarmResponse();
    }

    private OpenWeatherMapApiResponse coldMockedOpenWeatherMapApiResponse() {
        return OpenWeatherMapApiResponseSample.createSampleColdResponse();
    }
}
