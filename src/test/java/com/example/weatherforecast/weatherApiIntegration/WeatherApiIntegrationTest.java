package com.example.weatherforecast.weatherApiIntegration;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.integration.WeatherApiIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiIntegrationTest {


    WeatherApiIntegration weatherApiIntegration;
    City city;
    private final Double LATITUDE=50.4422;
    private final Double LONGITUDE=30.5367;
    private final String HOST = "https://api.open-meteo.com/v1/forecast?";

    @BeforeEach
    void setUp() {
        weatherApiIntegration = new WeatherApiIntegration();
        city = new City(LATITUDE,LONGITUDE);
    }

    @Test
    void findWeatherByLatitudeAndLongitude()  {
      WeatherJsonModel weatherJsonModel = weatherApiIntegration.findWeatherByLatitudeAndLongitude(city);
        assertNotNull(weatherJsonModel);
        assertEquals(168,weatherJsonModel.getHourly().getTime().size());
        assertEquals(168,weatherJsonModel.getHourly().getRain().size());
        assertEquals(168,weatherJsonModel.getHourly().getTemperature2m().size());
        LocalDate dateObj = LocalDate.now();
        assertEquals(dateObj + "T00:00",weatherJsonModel.getHourly().getTime().get(0));
    }
}