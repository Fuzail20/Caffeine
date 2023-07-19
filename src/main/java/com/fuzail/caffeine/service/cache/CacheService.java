package com.fuzail.caffeine.service.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fuzail.caffeine.enums.CacheName;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class CacheService {
    @Autowired
    CacheManager cacheManager;

    public Map<String, Object> getEntriesForCache(CacheName cacheName) throws JsonProcessingException {

        final com.github.benmanes.caffeine.cache.Cache<String, Object> cache = (Cache<String, Object>) cacheManager.getCache(cacheName.name()).getNativeCache();

        ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);


        final ConcurrentMap<String, Object> data = cache.asMap();
        final String json = objectMapper.writeValueAsString(data);

        final TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        return objectMapper.readValue(json, typeRef);
    }

    public String evictAllCacheValues(CacheName cacheName) {
        cacheManager.getCache(cacheName.name()).clear();
        return "SUCCESS";
    }

    public String evictCacheByKey(CacheName cacheName, String key) {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName.name());
        cache.evict(key);
        return "SUCCESS";
    }

    public String getValueFromCache(CacheName cacheName, String key) {
        CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;
        CaffeineCache cache = (CaffeineCache) caffeineCacheManager.getCache(cacheName.name());
        Cache<Object, Object> caffeine = cache.getNativeCache();
        Object obj = caffeine.getIfPresent(key);
        if (Objects.nonNull(obj)) {
            return String.valueOf(obj);
        }
        return null;
    }

    public Boolean putValueInCache(CacheName cacheName, String key, String value) {
        CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;
        CaffeineCache cache = (CaffeineCache) caffeineCacheManager.getCache(cacheName.name());
        Cache<Object, Object> caffeine = cache.getNativeCache();
        if(Objects.nonNull(caffeine)){
            caffeine.put(key, value);
            return true;
        }
        return false;
    }
}
