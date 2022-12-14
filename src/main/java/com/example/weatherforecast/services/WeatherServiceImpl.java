package com.example.weatherforecast.services;

import com.example.weatherforecast.converters.WeatherToWeatherResponseConverter;
import com.example.weatherforecast.dto.WeatherRequest;
import com.example.weatherforecast.dto.WeatherResponse;
import com.example.weatherforecast.dto.WeatherDto;
import com.example.weatherforecast.integration.WeatherApiIntegration;
import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.example.weatherforecast.integration.WeatherChannels;
import com.example.weatherforecast.repositories.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiIntegration weatherApiIntegration;
    private final WeatherRepository weatherRepository;
    private final WeatherChannels weatherChannels;
    private final WeatherToWeatherResponseConverter weatherToWeatherResponseConverter;

    private final int TIME_START_INDEX_IN_DATE_STRING = 11;
    private final int DATE_END_INDEX_IN_DATE_STRING = 10;
    private final String MORNING = "09:00";
    private final String MIDDAY = "14:00";
    private final String EVENING = "20:00";

    private String dateToday = String.valueOf(LocalDate.now());
    private int dateValue = Integer.parseInt(dateToday.substring(8, 10));
    private String datePlus1 = dateToday.substring(0, 8) + (dateValue + 1);
    private String datePlus2 = dateToday.substring(0, 8) + (dateValue + 2);
    private String datePlus3 = dateToday.substring(0, 8) + (dateValue + 3);
    private String datePlus4 = dateToday.substring(0, 8) + (dateValue + 4);
    private String datePlus5 = dateToday.substring(0, 8) + (dateValue + 5);
    private String datePlus6 = dateToday.substring(0, 8) + (dateValue + 6);


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
        return weatherRepository.findByCity(city);
    }

    @Override
    public List<Weather> getWeatherDataForTheRequestCity(City city) throws JsonProcessingException {

        List<Weather> weatherList = new ArrayList<>();
        boolean weatherFound = false;

        List<String> dataListNeedToReturn = dateListToReturn();

        List<Weather> weatherListFromDB = getWeatherListFromDB(city);

        for (Weather weather : weatherListFromDB) {

            String dateFromDb = weather.getDate();
            String timeFromDb = weather.getTime();

            if (!weatherFound ||
                    dateToday.equals(dateFromDb)) {
                weatherFound = true;
            }

            if (dataListNeedToReturn.contains(dateFromDb)
                    & weatherList.stream().noneMatch(w -> w.getDate().equals(dateFromDb)
                    & w.getTime().equals(timeFromDb))) {

                weatherList.add(weather);

            }
        }

        if (weatherList.size() == 21) {
            System.out.println("return from db");
            return weatherList;

        } else {
            List<Weather> weatherFromApi = getWeatherFromApi(city.getLatitude(), city.getLongitude());

            saveWeatherListToDB(weatherFromApi);

            System.out.println("return from api");

            return weatherFromApi;
        }
    }

    @Override
    @StreamListener(WeatherChannels.WEATHER_REQUEST_INPUT_CHANNEL)
    public void listenWeatherRequest(WeatherRequest weatherRequest) throws JsonProcessingException {

        log.info("Got request for weather {}", weatherRequest);

        List<Weather> weathers = getWeatherDataForTheRequestCity(
                new City(weatherRequest.getLatitude(), weatherRequest.getLongitude())
        );

        List <WeatherDto> weatherDtos = new ArrayList<>();

        weathers.forEach(weather ->
                weatherDtos.add(weatherToWeatherResponseConverter.convert(weather, weatherRequest)));

        WeatherResponse response = new WeatherResponse(weatherDtos, weatherRequest.getTripId());
        sendWeatherResponse(response);

    }

    @Override
    public void sendWeatherResponse(WeatherResponse weatherResponses) {

        weatherChannels.weatherResponse().send(MessageBuilder.withPayload(weatherResponses).build());

        log.info("Message {} published", weatherResponses);

    }

    private List<String> dateListToReturn() {

        List<java.lang.String> dataListNeedToReturn = new ArrayList<>();

        dataListNeedToReturn.add(dateToday);
        dataListNeedToReturn.add(datePlus1);
        dataListNeedToReturn.add(datePlus2);
        dataListNeedToReturn.add(datePlus3);
        dataListNeedToReturn.add(datePlus4);
        dataListNeedToReturn.add(datePlus5);
        dataListNeedToReturn.add(datePlus6);

        return dataListNeedToReturn;
    }

}
