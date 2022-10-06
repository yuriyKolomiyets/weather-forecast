package com.example.weatherforecast.controller;
import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
@RestController
public class WeatherController {

    @Value("${rest.baseurl}")
    private String host;
    private RestTemplate restTemplate;


    public WeatherJsonModel findWeatherByLatitudeAndLongitude(City city) throws JsonProcessingException {
        return restTemplate.getForObject (
                urlBuilder(city), WeatherJsonModel.class);
    }

    private String urlBuilder(City city) {
        return host +
                "latitude=" +
                city.getLatitude() + "&longitude" +
                city.getLongitude() +
                "&hourly=temperature_2m,rain";
    }


}
