package com.example.weatherforecast.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface WeatherChannels {

    String WEATHER_REQUEST_INPUT_CHANNEL = "weather-request-exchange";

    String WEATHER_RESPONSE_OUTPUT_CHANNEL = "weather-response-exchange";

    @Input(WEATHER_REQUEST_INPUT_CHANNEL)
    SubscribableChannel weatherRequest();

    @Output
    MessageChannel weatherResponse();
}
