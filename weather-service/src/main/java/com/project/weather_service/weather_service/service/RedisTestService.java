package com.project.weather_service.weather_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisTestService {

    private final RedisTemplate<String, Object> redisTestTemplate;

    @Autowired
    public RedisTestService(@Qualifier("redisTestTemplate") RedisTemplate<String, Object> redisTestTemplate) {
        this.redisTestTemplate = redisTestTemplate;
    }

    public void setData(String key, String value) {
        redisTestTemplate.opsForValue().set(key, value);
    }

    public void setDataWithExpiry(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTestTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public String getData(String key) {
        Object value = redisTestTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    public Boolean deleteData(String key) {
        return redisTestTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return redisTestTemplate.hasKey(key);
    }

    public Set<String> getAllKeys(String pattern) {
        return redisTestTemplate.keys(pattern);
    }

    public void setExpire(String key, long timeout, TimeUnit timeUnit) {
        redisTestTemplate.expire(key, timeout, timeUnit);
    }

    public Long getExpire(String key) {
        return redisTestTemplate.getExpire(key);
    }
}