package com.currencyapp.backend.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.currencyapp.backend.model.FrankfurterResponse;
import java.util.List;

@Component
public class FrankfurterClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.frankfurter.app";

    public FrankfurterResponse getLatestRates(String base, List<String> symbols) {
        String url = BASE_URL + "/latest?base=" + base + "&symbols=" + String.join(",", symbols);
        return restTemplate.getForObject(url, FrankfurterResponse.class);
    }

    public FrankfurterResponse getHistoricalRates(String base, String from, String to, List<String> symbols) {
        String url = BASE_URL + "/" + from + ".." + to + "?base=" + base + "&symbols=" + String.join(",", symbols);
        return restTemplate.getForObject(url, FrankfurterResponse.class);
    }
}