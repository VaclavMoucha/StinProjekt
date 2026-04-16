package com.currencyapp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.currencyapp.backend.model.ExchangeRate;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.service.RatesService;

@RestController
@RequestMapping("/api/rates")
public class RatesController {
    @Autowired
    private RatesService ratesService;

    @GetMapping
    public FrankfurterResponse getLatestRates() {
        return ratesService.getLatestRates();
    }

    @GetMapping("/strongest")
    public ExchangeRate getStrongest() {
        return ratesService.getStrongest();
    }

    @GetMapping("/weakest")
    public ExchangeRate getWeakest() {
        return ratesService.getWeakest();
    }

    @GetMapping("/average")
    public List<ExchangeRate> getAverage(@RequestParam String from,@RequestParam String to){

        return  ratesService.getAverage(from, to);
    }
}