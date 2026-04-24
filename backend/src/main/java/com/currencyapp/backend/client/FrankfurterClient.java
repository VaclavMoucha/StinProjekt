package com.currencyapp.backend.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.currencyapp.backend.model.FrankfurterHistoricalResponse;
import com.currencyapp.backend.model.FrankfurterResponse;
import com.currencyapp.backend.repository.SettingsRepository;

import java.util.List;
import java.util.Map;

@Component
public class FrankfurterClient {

    @Autowired
    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SettingsRepository.class);
    private static final String BASE_URL = "https://api.frankfurter.app";

    public FrankfurterResponse getLatestRates(String base, List<String> symbols) {
        String url = BASE_URL + "/latest?base=" + base + "&symbols=" + String.join(",", symbols);
        logger.debug("Calling Frankfurter API[Latest] with URL: {}", url);
        try {
            return restTemplate.getForObject(url, FrankfurterResponse.class);
        } catch (Exception e) {
            logger.error("Frankfurter API call failed: {}", e.getMessage());
            throw e;
        }

    }

    public FrankfurterHistoricalResponse getHistoricalRates(String base, String from, String to, List<String> symbols) {
        String url = BASE_URL + "/" + from + ".." + to + "?base=" + base + "&symbols=" + String.join(",", symbols);
        logger.debug("Calling Frankfurter API[Historical] with URL: {}", url);
        try {
            return restTemplate.getForObject(url, FrankfurterHistoricalResponse.class);
        } catch (Exception e) {
            logger.error("Frankfurter API call failed: {}", e.getMessage());
            throw e;
        }

    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getCurrencies() {
        String url = BASE_URL + "/currencies";
        logger.debug("Calling Frankfurter API[Currencies] with URL: {}", url);
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            logger.error("Frankfurter API call failed: {}", e.getMessage());
            throw e;
        }

    }
}