package com.project.weather_service.weather_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class AppConfig {

    private final String redisTestHost;
    private final int redisTestPort;

    public AppConfig(@Value("${redis.test.host}") String redisTestHost,
                     @Value("${redis.test.port}") int redisTestPort) {
        this.redisTestHost = redisTestHost;
        this.redisTestPort = redisTestPort;
    }

    @Bean(name = "redisTestConnectionFactory")
    public RedisConnectionFactory redisTestConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisTestHost);
        config.setPort(redisTestPort);
        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "redisTestTemplate")
    public RedisTemplate<String, Object> redisTestTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisTestConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
