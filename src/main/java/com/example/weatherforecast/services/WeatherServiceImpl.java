package com.example.weatherforecast.services;

import com.example.weatherforecast.weatherApiIntegration.WeatherApiIntegration;
import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiIntegration weatherApiIntegration;

    private final int TIME_START_INDEX_IN_DATE_STRING = 11;
    private final int DATE_END_INDEX_IN_DATE_STRING = 10;
    private final String MORNING = "09:00";
    private final String MIDDAY = "14:00";
    private final String EVENING = "20:00";

    public WeatherServiceImpl(WeatherApiIntegration weatherApiIntegration) {
        this.weatherApiIntegration = weatherApiIntegration;
    }

    @Override
    public WeatherJsonModel findWeatherByLatitudeAndLongitude(City city) throws JsonProcessingException {
        return weatherApiIntegration.findWeatherByLatitudeAndLongitude(city);
    }

    @Override
    public List<Weather> trimJSONto3ValuesDaily(@NotNull WeatherJsonModel weatherJsonModel) {
        List<Weather> weatherList = new ArrayList<>();
        Double latitude = weatherJsonModel.getLatitude();
        Double longitude = weatherJsonModel.getLongitude();

        for (int i = 0; i < weatherJsonModel.getHourly().getTime().size() - 1; i++) {

            String time = weatherJsonModel.getHourly()
                    .getTime().get(i)
                    .substring(TIME_START_INDEX_IN_DATE_STRING);

            String date = weatherJsonModel.getHourly()
                    .getTime().get(i)
                    .substring(0, DATE_END_INDEX_IN_DATE_STRING);

            Double temperature = weatherJsonModel.getHourly()
                    .getTemperature2m().get(i);

            Double rainProbability = weatherJsonModel.getHourly()
                    .getRain().get(i);

            if (time.equals(MORNING) || time.equals(MIDDAY) || time.equals(EVENING)) {

                Weather weatherToSave = Weather.builder()
                        .date(date)
                        .time(time)
                        .temperature(temperature)
                        .rainProbability(rainProbability)
                        .city(new City(latitude, longitude))
                        .build();

                weatherList.add(weatherToSave);
            }
        }
        return weatherList;
    }

    @Override
    public List<Weather> getWeather(Double latitude, Double longitude) throws JsonProcessingException {
        WeatherJsonModel weatherJSONModel =
                weatherApiIntegration.findWeatherByLatitudeAndLongitude(new City(latitude, longitude));
        return trimJSONto3ValuesDaily(weatherJSONModel);
    }


}
