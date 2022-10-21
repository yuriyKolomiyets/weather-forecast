package com.example.weatherforecast.controllers;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @RequestMapping(value = "/api/latitude/{latitude}/longitude/{longitude}", method = GET)
    public List <Weather> getWeather(@PathVariable Double latitude, @PathVariable Double longitude )
            throws JsonProcessingException {
        return weatherService.getWeatherDataForTheRequestCity(new City(latitude,longitude));
    }


}
