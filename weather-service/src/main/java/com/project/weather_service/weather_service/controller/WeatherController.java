package com.project.weather_service.weather_service.controller;

import com.project.weather_service.weather_service.entity.Weather;
import com.project.weather_service.weather_service.service.CacheInspectionService;
import com.project.weather_service.weather_service.service.RedisTestService;
import com.project.weather_service.weather_service.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CacheInspectionService cacheInspectionService;
    private final RedisTestService redisTestService;

    @Autowired
    public WeatherController(WeatherService weatherService,
                             CacheInspectionService cacheInspectionService,
                             RedisTestService redisTestService) {
        this.weatherService = weatherService;
        this.cacheInspectionService = cacheInspectionService;
        this.redisTestService = redisTestService;
    }

    @GetMapping("/{cityName}")
    public String getWeatherInfo(@PathVariable String cityName) {
        return weatherService.getWeatherByCityName(cityName);
    }

    @PostMapping("/add")
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherService.addWeather(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    @GetMapping("/cacheData")
    public void getCacheData() {
        cacheInspectionService.printCacheContents("weather");
    }

    @PutMapping("/update")
    public Weather updateWeather(@RequestBody Weather weather) {
        return weatherService.updateWeather(weather);
    }

    // Redis Test Endpoints
    @PostMapping("/redis-test/set")
    public String setRedisTestData(@RequestParam String key, @RequestParam String value) {
        redisTestService.setData(key, value);
        return "Data set successfully in redis-test";
    }

    @PostMapping("/redis-test/set-with-expiry")
    public String setRedisTestDataWithExpiry(@RequestParam String key, @RequestParam String value,
                                             @RequestParam long timeout, @RequestParam String timeUnit) {
        TimeUnit unit = TimeUnit.valueOf(timeUnit.toUpperCase());
        redisTestService.setDataWithExpiry(key, value, timeout, unit);
        return "Data set successfully in redis-test with expiry";
    }

    @GetMapping("/redis-test/get/{key}")
    public String getRedisTestData(@PathVariable String key) {
        String value = redisTestService.getData(key);
        return value != null ? value : "Key not found";
    }

    @DeleteMapping("/redis-test/delete/{key}")
    public String deleteRedisTestData(@PathVariable String key) {
        Boolean deleted = redisTestService.deleteData(key);
        return deleted ? "Key deleted successfully" : "Key not found";
    }

    @GetMapping("/redis-test/exists/{key}")
    public Boolean checkRedisTestKey(@PathVariable String key) {
        return redisTestService.hasKey(key);
    }

    @GetMapping("/redis-test/keys")
    public Set<String> getAllRedisTestKeys(@RequestParam(defaultValue = "*") String pattern) {
        return redisTestService.getAllKeys(pattern);
    }

    @PostMapping("/redis-test/expire/{key}")
    public String setRedisTestExpire(@PathVariable String key, @RequestParam long timeout, @RequestParam String timeUnit) {
        TimeUnit unit = TimeUnit.valueOf(timeUnit.toUpperCase());
        redisTestService.setExpire(key, timeout, unit);
        return "Expiry set successfully for key: " + key;
    }

    @GetMapping("/redis-test/ttl/{key}")
    public Long getRedisTestTTL(@PathVariable String key) {
        return redisTestService.getExpire(key);
    }
}