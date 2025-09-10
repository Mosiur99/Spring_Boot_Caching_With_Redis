package com.project.weather_service.weather_service.service;

import com.project.weather_service.weather_service.entity.Weather;
import com.project.weather_service.weather_service.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    @Cacheable(value = "weather", key = "#cityName")
    public String getWeatherByCityName(String cityName) {
        System.out.println("getWeatherByCityName: " + cityName);
        Optional<Weather> weather = weatherRepository.findByCityName(cityName);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    @Override
    @Transactional
    public Weather addWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @Override
    @Transactional
    @CachePut(value = "weather", key = "#weather.cityName")
    public Weather updateWeather(Weather weather) {
        Optional<Weather> oldWeather = weatherRepository.findByCityName(weather.getCityName());
        if (oldWeather.isPresent()) {
            oldWeather.get().setForecast(weather.getForecast());
            weatherRepository.save(oldWeather.get());
            return oldWeather.get();
        }
        return null;
    }
}
