package com.currencyapp.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.currencyapp.backend.model.ExchangeRate;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.service.RatesService;

@ExtendWith(MockitoExtension.class)
public class RatesControllerTest {

    @Mock
    private RatesService ratesService;

    @InjectMocks
    private RatesController ratesController;

    @Test
    void getLatestRates_returnsResponse() {
        FrankfurterResponse response = new FrankfurterResponse("EUR", "2024-01-01",
                Map.of("USD", 1.08, "CZK", 24.5));
        when(ratesService.getLatestRates()).thenReturn(response);

        FrankfurterResponse result = ratesController.getLatestRates();

        assertEquals("EUR", result.getBase());
    }

    @Test
    void getStrongest_returnsExchangeRate() {
        ExchangeRate rate = new ExchangeRate("CZK", 24.5, "2024-01-01");
        when(ratesService.getStrongest()).thenReturn(rate);

        ExchangeRate result = ratesController.getStrongest();

        assertEquals("CZK", result.getCurrency());
    }

    @Test
    void getWeakest_returnsExchangeRate() {
        ExchangeRate rate = new ExchangeRate("GBP", 0.85, "2024-01-01");
        when(ratesService.getWeakest()).thenReturn(rate);

        ExchangeRate result = ratesController.getWeakest();

        assertEquals("GBP", result.getCurrency());
    }

    @Test
    void getCurrencies_returnsMap() {
        Map<String, String> currencies = Map.of("USD", "US Dollar", "CZK", "Czech Koruna");
        when(ratesService.getCurrencies()).thenReturn(currencies);

        Map<String, String> result = ratesController.getCurrencies();

        assertEquals(2, result.size());
    }
}