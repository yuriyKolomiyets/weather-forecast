package com.example.weatherforecast.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "weathers")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Weather implements Serializable {

    private String date;
    private String time;
    private Double temperature;
    private Double rainProbability;
    private City city;

}
