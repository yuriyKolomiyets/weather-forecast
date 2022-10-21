package com.example.weatherforecast.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "weathers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Weather implements Serializable {

    private String date;
    private String time;
    private Double temperature;
    private Double rainProbability;
    private City city;

}
