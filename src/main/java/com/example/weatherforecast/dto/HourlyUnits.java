package com.example.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HourlyUnits {
    private String time;
    @JsonProperty("temperature_2m")
    private String temperature2m;
    private String rain;
}
