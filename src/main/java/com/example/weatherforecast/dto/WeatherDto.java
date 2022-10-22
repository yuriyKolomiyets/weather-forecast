package com.example.weatherforecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherDto {

    private String date;
    private String time;
    private Double temperature;
    private Double rainProbability;
}
