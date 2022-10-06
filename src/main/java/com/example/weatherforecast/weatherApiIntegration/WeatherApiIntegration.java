package com.example.weatherforecast.weatherApiIntegration;
import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.dto.WeatherJsonModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiIntegration {

   //@Value("${rest.base.path}") not working
    private String host = "https://api.open-meteo.com/v1/forecast?";
    private RestTemplate restTemplate;

    public WeatherApiIntegration() {
        this.host = host;
        this.restTemplate = new RestTemplate();
    }

    public WeatherJsonModel findWeatherByLatitudeAndLongitude(City city) throws JsonProcessingException {
        return restTemplate.getForObject (
                urlBuilder(city), WeatherJsonModel.class);
    }

    private String urlBuilder(City city) {
        return host +
                "latitude=" +
                city.getLatitude() + "&longitude=" +
                city.getLongitude() +
                "&hourly=temperature_2m,rain";
    }


}
