package com.currencyapp.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.currencyapp.backend.client.FrankfurterClient;
import com.currencyapp.backend.exception.SettingsNotFoundException;
import com.currencyapp.backend.model.ExchangeRate;
import com.currencyapp.backend.model.FrankfurterHistoricalResponse;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.model.UserSettings;
import com.currencyapp.backend.repository.LogRepository;
import com.currencyapp.backend.repository.SettingsRepository;

@ExtendWith(MockitoExtension.class)
public class RatesServicTest {
    @Mock
    private FrankfurterClient frankfurterClient;
    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private RatesService ratesService;

    @Test
    void getStrongest_returnsCorrectCurrency() {

        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK", "GBP"));
        FrankfurterResponse response = new FrankfurterResponse("EUR", "2024-01-01",
                Map.of("USD", 1.08, "CZK", 24.5, "GBP", 0.85));

        when(settingsRepository.getSettings()).thenReturn(settings);
        when(frankfurterClient.getLatestRates(any(), any())).thenReturn(response);

        ExchangeRate result = ratesService.getStrongest();

        assertEquals("CZK", result.getCurrency());
        assertEquals(24.5, result.getRate());
    }

    @Test
    void getWeakest_returnsCorrectCurrency() {
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK", "GBP"));
        FrankfurterResponse response = new FrankfurterResponse("EUR", "2024-01-01",
                Map.of("USD", 1.08, "CZK", 24.5, "GBP", 0.85));

        when(settingsRepository.getSettings()).thenReturn(settings);
        when(frankfurterClient.getLatestRates(any(), any())).thenReturn(response);

        ExchangeRate result = ratesService.getWeakest();

        assertEquals("GBP", result.getCurrency());
        assertEquals(0.85, result.getRate());
    }

    @Test
    void getAverage_returnsCorrectAverage() {
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK"));
        Map<String, Map<String, Double>> rates = new HashMap<>();
        rates.put("2024-01-01", Map.of("USD", 1.08, "CZK", 24.5));
        rates.put("2024-01-02", Map.of("USD", 1.10, "CZK", 24.7));

        FrankfurterHistoricalResponse response = new FrankfurterHistoricalResponse(
                "EUR", "2024-01-01", "2024-01-02", rates);

        when(settingsRepository.getSettings()).thenReturn(settings);
        when(frankfurterClient.getHistoricalRates(any(), any(), any(), any())).thenReturn(response);

        List<ExchangeRate> result = ratesService.getAverage("2024-01-01", "2024-01-02");

        assertEquals(2, result.size());
    }

    @Test
    void getLatestRates_throwsWhenSettingsNull() {
        when(settingsRepository.getSettings()).thenReturn(null);

        assertThrows(SettingsNotFoundException.class, () -> {
            ratesService.getLatestRates();

        });
    }
}