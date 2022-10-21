package com.example.weatherforecast.integration;
import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.dto.WeatherJsonModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiIntegration {

    //@Value("${rest.base.path}")
    private final String host;
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherApiIntegration() {
        this.host ="https://api.open-meteo.com/v1/forecast?";
    }

    public WeatherJsonModel findWeatherByLatitudeAndLongitude(City city) {
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
