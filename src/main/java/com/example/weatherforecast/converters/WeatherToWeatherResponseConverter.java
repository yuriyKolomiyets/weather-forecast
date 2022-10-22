package com.example.weatherforecast.converters;

import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.dto.WeatherRequest;
import com.example.weatherforecast.dto.WeatherResponse;
import org.springframework.stereotype.Component;

@Component
public class WeatherToWeatherResponseConverter {

    public WeatherResponse convert(Weather weather, WeatherRequest request) {
        return new WeatherResponse(weather.getDate(), weather.getTime(), weather.getTemperature(),
                weather.getRainProbability(), request.getTripId());
    }
}
