package com.example.weatherforecast.services;

import com.example.weatherforecast.config.MongoConfig;
import com.example.weatherforecast.dto.HourlyUnits;
import com.example.weatherforecast.integration.WeatherApiIntegration;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.Hourly;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.repositories.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private WeatherApiIntegration weatherApiIntegration;

    private WeatherJsonModel returnWeatherJsonModel;

    @BeforeEach
    void setUp() {

        List <String> returnHourlyTime = new ArrayList();
        returnHourlyTime.add("2022-10-05T03:00");
        returnHourlyTime.add("2022-10-05T09:00");
        returnHourlyTime.add("2022-10-05T14:00");
        returnHourlyTime.add("2022-10-05T20:00");
        returnHourlyTime.add("2022-10-05T24:00");

        List <Double> returnTemperature2m = new ArrayList();
        returnTemperature2m.add(13.1);
        returnTemperature2m.add(13.2);
        returnTemperature2m.add(13.3);
        returnTemperature2m.add(13.4);
        returnTemperature2m.add(13.5);

        List <Double> returnRain = new ArrayList();
        returnRain.add(0.1);
        returnRain.add(0.2);
        returnRain.add(0.3);
        returnRain.add(0.4);
        returnRain.add(0.5);

       returnWeatherJsonModel = WeatherJsonModel.builder().latitude(11111.11).longitude(2222.22)
                .hourlyUnits(new HourlyUnits("iso8601", "°C", "mm"))
                .hourly(new Hourly(returnHourlyTime, returnTemperature2m, returnRain))
                .build();}

    @Test
    void trimJSONto3ValuesDaily() {
        WeatherService weatherService = new WeatherServiceImpl(weatherApiIntegration, weatherRepository);
        List<Weather> weatherList = weatherService.trimJSONto3ValuesDaily(returnWeatherJsonModel);
        assertNotNull(weatherList);
        assertEquals(3, weatherList.size());
        assertEquals("09:00", weatherList.get(0).getTime());
        assertEquals("2022-10-05", weatherList.get(0).getDate());
    }

    @Test
    void saveWeatherList(){
        WeatherService weatherService = new WeatherServiceImpl(weatherApiIntegration, weatherRepository);
        List<Weather> weatherList = weatherService.trimJSONto3ValuesDaily(returnWeatherJsonModel);
        List<Weather> weatherList1 = weatherService.saveWeatherList(weatherList);
        assertNotNull(weatherList1);
    }
}