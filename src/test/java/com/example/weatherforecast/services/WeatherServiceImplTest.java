package com.example.weatherforecast.services;

import com.example.weatherforecast.controller.WeatherController;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.Hourly;
import com.example.weatherforecast.dto.HourlyUnits;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.repositories.WeatherRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    WeatherRepository weatherRepository;

    @Mock
    WeatherService weatherService;

    private WeatherJsonModel returnWeatherJsonModel;

    @BeforeEach
    void setUp() {
        ArrayList <String> returnHourlyTime = new ArrayList();
        returnHourlyTime.add("2022-10-05T03:00");
        returnHourlyTime.add("2022-10-05T09:00");
        returnHourlyTime.add("2022-10-05T14:00");
        returnHourlyTime.add("2022-10-05T20:00");
        returnHourlyTime.add("2022-10-05T24:00");

        ArrayList <Double> returnTemperature2m = new ArrayList();
        returnTemperature2m.add(13.1);
        returnTemperature2m.add(13.2);
        returnTemperature2m.add(13.3);
        returnTemperature2m.add(13.4);
        returnTemperature2m.add(13.5);

        ArrayList <Double> returnRain = new ArrayList();
        returnRain.add(0.1);
        returnRain.add(0.2);
        returnRain.add(0.3);
        returnRain.add(0.4);
        returnRain.add(0.5);

       returnWeatherJsonModel = WeatherJsonModel.builder().latitude(11111.11).longitude(2222.22)
                .hourlyUnits(new HourlyUnits("iso8601", "°C", "mm"))
                .hourly(new Hourly(returnHourlyTime, returnTemperature2m, returnRain))
                .build();
    }

    @Test
    void findWeatherByLatitudeAndLongitude() throws JsonProcessingException {
        when(weatherService.findWeatherByLatitudeAndLongitude(any())).thenReturn(returnWeatherJsonModel);
        //
    }

    @Test
    void trimJSONto3ValuesDaily() {
        List<Weather> weatherList = weatherService.trimJSONto3ValuesDaily(returnWeatherJsonModel);
        assertNotNull(weatherList);
        assertTrue(weatherList.size() == 3);
        assertTrue(weatherList.get(0).getTime().equals("03:00"));
        assertTrue(weatherList.get(0).getDate().equals("2022-10-05"));
    }
}