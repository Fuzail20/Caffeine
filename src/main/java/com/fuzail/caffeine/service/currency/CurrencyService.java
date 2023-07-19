package com.fuzail.caffeine.service.currency;

import com.fuzail.caffeine.model.CurrencyConvertResponse;
import com.fuzail.caffeine.service.external.FixerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@CacheConfig(cacheNames = {"CURRENCY_CONVERTER_CACHE"})
@Slf4j
public class CurrencyService {
    @Autowired
    private FixerService fixerService;

    //@Cacheable(key="#applicationNumber",unless = "#result == null")
    @Cacheable(value="CURRENCY_CONVERTER_CACHE",keyGenerator = "customKeyGenerator",unless = "")
    public Double convertCurrency(String toCurrency , String fromCurrency , String amount){
        log.info("Cache MISS!!");
        try{
            CurrencyConvertResponse currencyConvertResponse = fixerService.convertCurrency(toCurrency,fromCurrency,amount);
            return currencyConvertResponse.getResult();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
