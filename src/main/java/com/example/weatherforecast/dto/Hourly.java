package com.example.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Hourly {
    private ArrayList<String> time;
    @JsonProperty("temperature_2m")
    private ArrayList<Double> temperature2m;
    private ArrayList<Double> rain;
}
