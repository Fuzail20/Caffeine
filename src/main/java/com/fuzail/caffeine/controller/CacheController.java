package com.fuzail.caffeine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fuzail.caffeine.enums.CacheName;
import com.fuzail.caffeine.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @GetMapping(value = "/data")
    public ResponseEntity<Map<String, Object>> getCacheData(@RequestParam(name = "cache-name") CacheName cacheName) throws JsonProcessingException {
        return ResponseEntity.ok(cacheService.getEntriesForCache(cacheName));
    }

    @PostMapping(value = "/evict-all")
    public ResponseEntity<String> evictAllCacheValues(@RequestParam(name = "cache-name") CacheName cacheName) {
        return ResponseEntity.ok(cacheService.evictAllCacheValues(cacheName));
    }

    @PostMapping(value = "/evict")
    public ResponseEntity<String> evictCacheByKey(@RequestParam(name = "cache-name") CacheName cacheName,
                                                  @RequestParam(name = "key") String key) {
        return ResponseEntity.ok(cacheService.evictCacheByKey(cacheName, key));
    }

    @GetMapping(value = "/cache-value")
    public ResponseEntity<String> getValueFromCache(@RequestParam(name = "cache-name") CacheName cacheName,
                                                    @RequestParam(name = "key") String key) {
        return ResponseEntity.ok(cacheService.getValueFromCache(cacheName, key));
    }

    @GetMapping(value = "/cache-put")
    public ResponseEntity<Boolean> putValueInCache(@RequestParam(name = "cache-name") CacheName cacheName,
                                                   @RequestParam(name = "key") String key,
                                                   @RequestParam(name = "value") String value) {
        return ResponseEntity.ok(cacheService.putValueInCache(cacheName, key, value));
    }

}

