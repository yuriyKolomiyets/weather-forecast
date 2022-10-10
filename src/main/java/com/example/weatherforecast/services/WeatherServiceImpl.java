package com.example.weatherforecast.services;

import com.example.weatherforecast.integration.WeatherApiIntegration;
import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.repositories.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiIntegration weatherApiIntegration;
    private final WeatherRepository weatherRepository;

    private final int TIME_START_INDEX_IN_DATE_STRING = 11;
    private final int DATE_END_INDEX_IN_DATE_STRING = 10;
    private final String MORNING = "09:00";
    private final String MIDDAY = "14:00";
    private final String EVENING = "20:00";

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
    public List<Weather> getWeatherFromApi(Double latitude, Double longitude) throws JsonProcessingException {
        WeatherJsonModel weatherJSONModel =
                weatherApiIntegration.findWeatherByLatitudeAndLongitude(new City(latitude, longitude));
        return trimJSONto3ValuesDaily(weatherJSONModel);
    }

    @Override
    public List<Weather> saveWeatherListToDB(List<Weather> weatherList) {
        return weatherRepository.saveAll(weatherList);

    }

    @Override
    public List<Weather> getWeatherListFromDB(City city) {
        return weatherRepository.findBy(city); // if null?
    }

    @Override
    public List<Weather> getWeatherToController(City city) throws JsonProcessingException {

        List<Weather> weatherList = new ArrayList<>();

        String dateToday = String.valueOf(LocalDate.now());
        int dateValue = Integer.parseInt(dateToday.substring(8,10));

        String datePlus1 = dateToday.substring(0,8) + (dateValue + 1);
        String datePlus2 = dateToday.substring(0,8) + (dateValue + 2);
        String datePlus3 = dateToday.substring(0,8) + (dateValue + 3);
        String datePlus4 = dateToday.substring(0,8) + (dateValue + 4);
        String datePlus5 = dateToday.substring(0,8) + (dateValue + 5);
        String datePlus6 = dateToday.substring(0,8) + (dateValue + 6);

        boolean weatherFound = false;

        if (getWeatherListFromDB(city) != null) {

            List<Weather> weatherListFromDB = getWeatherListFromDB(city);

            for (int i = 0; i < weatherListFromDB.size(); i++) {

                String dateFromDb = weatherListFromDB.get(i).getDate();


                if (!weatherFound ||
                        dateToday.equals(dateFromDb)) {
                    weatherFound = true;

                }

                if (weatherFound & (dateFromDb.equals(dateToday) || dateFromDb.equals(datePlus1)
                        || dateFromDb.equals(datePlus2) || dateFromDb.equals(datePlus3)
                        || dateFromDb.equals(datePlus4) || dateFromDb.equals(datePlus5)
                        || dateFromDb.equals(datePlus6))){

                    //need to check if already in array

                    weatherList.add(weatherListFromDB.get(i));
                }
            }

            System.out.println("return from db");

            return weatherList;
        }

        List<Weather> weatherFromApi = getWeatherFromApi(city.getLatitude(), city.getLongitude());
        saveWeatherListToDB(weatherFromApi);
        System.out.println("return from api");
        return weatherFromApi;
    }


}
