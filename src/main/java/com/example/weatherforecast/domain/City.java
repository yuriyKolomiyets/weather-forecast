package com.example.weatherforecast.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter
@Setter
@AllArgsConstructor
public class City implements Serializable {
    private Double latitude;
    private Double longitude;
}
