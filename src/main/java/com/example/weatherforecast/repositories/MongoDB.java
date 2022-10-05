package com.example.weatherforecast.repositories;

import com.example.weatherforecast.domain.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDB extends MongoRepository<Weather, Long> {

    @Query("")
    Weather findWeatherByCity(String city, String date);

    @Query("")
    Weather saveWeatherByCityAndDate(String city,String date);

}
