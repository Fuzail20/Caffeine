package com.fuzail.caffeine.controller;

import com.fuzail.caffeine.service.currency.CurrencyService;
import com.fuzail.caffeine.service.external.FixerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private FixerService fixerService;

    @Autowired
    private CurrencyService currencyService;

    @PostMapping("/convert")
    public ResponseEntity<Double> convertCurrency(@RequestParam(value="toCurrency") String toCurrency,
                                                  @RequestParam(value="fromCurrency") String fromCurrency,
                                                  @RequestParam(value="amount") String amount) throws IOException {
        Double response = currencyService.convertCurrency(toCurrency,fromCurrency,amount);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
