package com.example.weatherforecast.repositories;

import com.example.weatherforecast.domain.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherRepository extends MongoRepository<Weather, Long> {

}
