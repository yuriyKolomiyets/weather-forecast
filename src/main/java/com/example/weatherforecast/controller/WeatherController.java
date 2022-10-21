package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
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
        return weatherService.getWeatherToController(new City(latitude,longitude));
    }

    @GetMapping("/rabbit/{latitude}/{longitude}")
    public String publishMessage(@PathVariable Double latitude, @PathVariable Double longitude) {
        weatherService.sendWeatherResponse(WeatherJsonModel.builder().latitude(latitude).longitude(longitude).build());
        return "message published";
    }


}
