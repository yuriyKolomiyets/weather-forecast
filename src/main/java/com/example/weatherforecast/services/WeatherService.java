package com.example.weatherforecast.services;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface WeatherService {
    WeatherJsonModel findWeatherByLatitudeAndLongitude (City city) throws JsonProcessingException;
    List<Weather>trimJSONto3ValuesDaily(WeatherJsonModel weatherJsonModel);
    List<Weather>getWeather(Double latitude, Double longitude) throws JsonProcessingException;
}

