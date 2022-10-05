package com.example.weatherforecast.services;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${rest.baseurl}")
    private String host;

    private RestTemplate restTemplate;
    private final int TIME_START_INDEX_IN_DATE_STRING = 10;
    private final int DATE_END_INDEX_IN_DATE_STRING = 9;
    private final String MORNING = "09:00";
    private final String MIDDAY = "14:00";
    private final String EVENING = "20:00";

    @Override
    public WeatherJsonModel findWeatherByLatitudeAndLongitude(City city) throws JsonProcessingException {
        return restTemplate.getForObject (
                        urlBuilder(city), WeatherJsonModel.class);
    }

    @Override
    public List<Weather> trimJSONto3ValuesDaily(@NotNull WeatherJsonModel weatherJsonModel) {

        List<Weather> weatherList = new ArrayList<>();
        Double latitude = weatherJsonModel.getLatitude();
        Double longitude = weatherJsonModel.getLongitude();

        for (int i = 0; i < weatherJsonModel.getHourly().getTime().size(); i++) {

            String time = weatherJsonModel.getHourly()
                    .getTime().get(i)
                    .substring(TIME_START_INDEX_IN_DATE_STRING);

            String date = weatherJsonModel.getHourly()
                    .getTime().get(i)
                    .substring(0,DATE_END_INDEX_IN_DATE_STRING);

            Double temperature = weatherJsonModel.getHourly()
                    .getTemperature2m().get(i);

            Double rainProbability = weatherJsonModel.getHourly()
                    .getRain().get(i);

            if(time.equals(MORNING) || time.equals(MIDDAY) || time.equals(EVENING)){

                Weather weatherToSave = Weather.builder()
                        .date(date)
                        .time(time)
                        .temperature(temperature)
                        .rainProbability(rainProbability)
                        .city(new City(latitude,longitude))
                        .build();

                weatherList.add(weatherToSave);
            }
        }
        return weatherList;
    }


    private String urlBuilder(City city) {
        return host +
                "latitude=" +
                city.getLatitude() + "&longitude" +
                city.getLongitude() +
                "&hourly=temperature_2m,rain";
    }
}
