package com.fuzail.caffeine.config;

import com.fuzail.caffeine.enums.CacheName;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.springframework.cache.caffeine.CaffeineCacheManager;


import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setAllowNullValues(false);
        manager.registerCustomCache(CacheName.CURRENCY_CONVERTER_CACHE.name(), currencyConverterCacheConfigBuilder());
        manager.setCacheNames(Collections.emptyList());
        return manager;
    }

    Cache<Object, Object> currencyConverterCacheConfigBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(800)
                .maximumSize(3000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .removalListener(new CustomRemovalListener())
                .recordStats()
                .build();
    }
    class CustomRemovalListener implements RemovalListener<Object, Object> {
        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            log.debug("removal listener called with key {}, cause {}, evicted {}\n", key, cause.toString(), cause.wasEvicted());
        }
    }
}
