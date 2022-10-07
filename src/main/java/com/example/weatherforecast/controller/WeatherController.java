package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/weather")
@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping(path= {"{latitude}", "{longitude}"})
    public List <Weather> getWeather(@PathVariable("latitude") Double latitude,
                                     @PathVariable("longitude") Double longitude ) throws JsonProcessingException {
        return weatherService.getWeather(latitude, longitude);
    }

}
