package com.project.weather_service.weather_service.service;

import com.project.weather_service.weather_service.entity.Weather;

import java.util.List;

public interface WeatherService {

    String getWeatherByCityName(String cityName);

    Weather addWeather(Weather weather);

    List<Weather> getAllWeather();

    Weather updateWeather(Weather weather);
}
