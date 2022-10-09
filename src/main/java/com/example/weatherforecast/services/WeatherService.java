package com.example.weatherforecast.services;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface WeatherService {
    List<Weather>trimJSONto3ValuesDaily(WeatherJsonModel weatherJsonModel);
    List<Weather> getWeatherFromApi(Double latitude, Double longitude) throws JsonProcessingException;
    List<Weather> saveWeatherListToDB(List<Weather> weatherList);
    List<Weather> getWeatherListFromDB(City city);
    List<Weather> getWeatherToController(City city) throws JsonProcessingException;
}

