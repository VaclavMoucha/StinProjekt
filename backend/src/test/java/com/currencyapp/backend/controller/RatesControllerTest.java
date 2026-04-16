package com.currencyapp.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
}