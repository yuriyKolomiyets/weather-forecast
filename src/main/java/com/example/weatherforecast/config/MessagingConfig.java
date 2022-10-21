package com.example.weatherforecast.config;

import com.example.weatherforecast.integration.WeatherChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(WeatherChannels.class)
public class MessagingConfig {
}
