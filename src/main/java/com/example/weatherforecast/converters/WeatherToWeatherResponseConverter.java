package com.example.weatherforecast.converters;

import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherRequest;
import com.example.weatherforecast.dto.WeatherDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherToWeatherResponseConverter {

    public WeatherDto convert(Weather weather, WeatherRequest request) {
        return new WeatherDto(weather.getDate(), weather.getTime(), weather.getTemperature(),
                weather.getRainProbability());
    }
}
