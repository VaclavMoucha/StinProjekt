package com.currencyapp.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.currencyapp.backend.model.ExchangeRate;
import com.currencyapp.backend.model.FrankfurterHistoricalResponse;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.service.RatesService;
import jakarta.validation.constraints.Pattern;

@Validated
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
    public List<ExchangeRate> getAverage(
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format") String from,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format") String to) {

        return ratesService.getAverage(from, to);
    }

    @GetMapping("/currencies")
    public Map<String, String> getCurrencies() {
        return ratesService.getCurrencies();
    }
    @GetMapping("/historical")
    public FrankfurterHistoricalResponse getHistoricalRates(
            @RequestParam String from,
            @RequestParam String to) {
        return ratesService.getHistoricalRates(from, to);
    }
}