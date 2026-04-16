package com.currencyapp.backend.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.currencyapp.backend.client.FrankfurterClient;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.model.LogEntry;
import com.currencyapp.backend.repository.LogRepository;
import com.currencyapp.backend.repository.SettingsRepository;

@Service
public class RatesService {
    @Autowired
    private FrankfurterClient frankfurterClient;
    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private LogRepository logRepository;

    public FrankfurterResponse getLatestRates() {
        var settings = settingsRepository.getSettings();
        var response = frankfurterClient.getLatestRates(
                settings.getPreferredCurrency(),
                settings.getSelectedCurrencies());
        logRepository.addLog(new LogEntry(
                LocalDateTime.now().toString(),
                "INFO",
                "Fetched latest rates for " + settings.getPreferredCurrency()));
        return response;
    }

}