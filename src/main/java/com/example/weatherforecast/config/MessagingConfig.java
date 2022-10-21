package com.example.weatherforecast.config;

import com.example.weatherforecast.messaging.WeatherChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableBinding(WeatherChannels.class)
public class MessagingConfig {
}
