package com.project.weather_service.weather_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheInspectionService {

    private final CacheManager cacheManager;

    @Autowired
    public CacheInspectionService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void printCacheContents(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (Objects.nonNull(cache)) {
            System.out.println("Cache contents in cache: " );
            System.out.println(Objects.requireNonNull(cache.getNativeCache()));
        } else {
            System.out.println("Cache not in cache");
        }
    }
}
