package com.example.weatherforecast.services;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.dto.WeatherRequest;
import com.example.weatherforecast.dto.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface WeatherService {
    List<Weather>trimJSONto3ValuesDaily(WeatherJsonModel weatherJsonModel);
    List<Weather> getWeatherFromApi(Double latitude, Double longitude) throws JsonProcessingException;
    List<Weather> saveWeatherListToDB(List<Weather> weatherList);
    List<Weather> getWeatherListFromDB(City city);
    List<Weather> getWeatherDataForTheRequestCity(City city) throws JsonProcessingException;
    void listenWeatherRequest(WeatherRequest weatherRequest) throws JsonProcessingException;
    public void sendWeatherResponse(List<WeatherResponse> weatherResponses);
}

