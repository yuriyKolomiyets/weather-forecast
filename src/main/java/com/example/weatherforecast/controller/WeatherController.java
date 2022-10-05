package com.example.weatherforecast.controller;
import com.example.weatherforecast.services.WeatherService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


}
