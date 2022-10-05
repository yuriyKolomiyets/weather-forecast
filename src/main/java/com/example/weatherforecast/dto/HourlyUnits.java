package com.example.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyUnits {
    private String time;
    @JsonProperty("temperature_2m")
    private String temperature2m;
    private String rain;
}
