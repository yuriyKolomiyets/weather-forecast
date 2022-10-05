package com.example.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherJsonModel {
    private Double latitude;
    private Double longitude;
    @JsonProperty("hourly_units")
    private HourlyUnits hourlyUnits;
    private Hourly hourly;
}
