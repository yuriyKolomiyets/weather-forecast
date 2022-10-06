package com.example.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherJsonModel {
    private Double latitude;
    private Double longitude;
    @JsonProperty("hourly_units")
    private HourlyUnits hourlyUnits;
    private Hourly hourly;

}
