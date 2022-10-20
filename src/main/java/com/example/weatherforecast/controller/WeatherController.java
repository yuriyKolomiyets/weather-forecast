package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.example.weatherforecast.services.RabbitMQSender;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final RabbitMQSender rabbitMqSender;

    @RequestMapping(value = "/api/latitude/{latitude}/longitude/{longitude}", method = GET)
    public List <Weather> sendWeather(@PathVariable Double latitude, @PathVariable Double longitude)
            throws JsonProcessingException {
        System.out.println("request cathched");
        return weatherService.getWeatherToController(new City(latitude,longitude));
    }

    @RequestMapping(value = "/api-rabbit/latitude/{latitude}/longitude/{longitude}", method = GET)
    public void sendWeatherWithRabbit(@PathVariable Double latitude, @PathVariable Double longitude)
            throws JsonProcessingException {
        List<Weather> weather = weatherService.getWeatherToController(new City(latitude, longitude));
        weather.forEach(rabbitMqSender::send);
    }




}
