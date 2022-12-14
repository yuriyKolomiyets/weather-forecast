package com.example.weatherforecast.repositories;

import com.example.weatherforecast.domain.City;
import com.example.weatherforecast.domain.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WeatherRepository extends MongoRepository<Weather, Long> {

    public List<Weather> findByCity(City city);

}
