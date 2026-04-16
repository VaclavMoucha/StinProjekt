package com.currencyapp.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.currencyapp.backend.client.FrankfurterClient;
import com.currencyapp.backend.exception.SettingsNotFoundException;
import com.currencyapp.backend.model.ExchangeRate;
import com.currencyapp.backend.model.FrankfurterHistoricalResponse;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.model.LogEntry;
import com.currencyapp.backend.repository.LogRepository;
import com.currencyapp.backend.repository.SettingsRepository;

@Service
public class RatesService {
    private static final Logger logger = LoggerFactory.getLogger(RatesService.class);
    @Autowired
    private FrankfurterClient frankfurterClient;
    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private LogRepository logRepository;

    public FrankfurterResponse getLatestRates() {
        var settings = settingsRepository.getSettings();
        if (settings == null) {
            logger.info("Settings not found when fetching latest rates");
            throw new SettingsNotFoundException();
        }
        logger.info("Fetching latest rates for {}", settings.getPreferredCurrency());
        try {
            var response = frankfurterClient.getLatestRates(
                    settings.getPreferredCurrency(),
                    settings.getSelectedCurrencies());
            logRepository.addLog(new LogEntry(
                    LocalDateTime.now().toString(),
                    "INFO",
                    "Fetched latest rates for " + settings.getPreferredCurrency()));
            return response;
        } catch (Exception e) {
            logger.error("API call failed: {}", e.getMessage());
            logRepository.addLog(new LogEntry(
                    LocalDateTime.now().toString(),
                    "ERROR",
                    "API call failed: " + e.getMessage()));
            throw e;
        }
    }

    public ExchangeRate getStrongest() {
        FrankfurterResponse response = getLatestRates();
        Map<String, Double> rates = response.getRates();
        Map.Entry<String, Double> strongest = rates.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        return new ExchangeRate(strongest.getKey(), strongest.getValue(), response.getDate());
    }

    public ExchangeRate getWeakest() {
        FrankfurterResponse response = getLatestRates();
        Map<String, Double> rates = response.getRates();
        Map.Entry<String, Double> weakest = rates.entrySet().stream().min(Map.Entry.comparingByValue()).get();
        return new ExchangeRate(weakest.getKey(), weakest.getValue(), response.getDate());
    }

    public List<ExchangeRate> getAverage(String from, String to) {
        var settings = settingsRepository.getSettings();
        FrankfurterHistoricalResponse response = frankfurterClient.getHistoricalRates(
                settings.getPreferredCurrency(), from, to,
                settings.getSelectedCurrencies());

        Map<String, List<Double>> ratesPerCurrency = new HashMap<>();

        for (Map<String, Double> dayRates : response.getRates().values()) {
            for (Map.Entry<String, Double> entry : dayRates.entrySet()) {
                ratesPerCurrency
                        .computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                        .add(entry.getValue());
            }
        }

        List<ExchangeRate> result = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : ratesPerCurrency.entrySet()) {
            double average = entry.getValue().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .getAsDouble();
            result.add(new ExchangeRate(entry.getKey(), average, from + " to " + to));
        }

        return result;
    }

    public Map<String, String> getCurrencies() {
        logger.info("Fetching available currencies");
        return frankfurterClient.getCurrencies();
    }
}