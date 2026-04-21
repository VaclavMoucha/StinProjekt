package com.currencyapp.backend.client;

import com.currencyapp.backend.model.FrankfurterHistoricalResponse;
import com.currencyapp.backend.model.FrankfurterResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FrankfurterClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FrankfurterClient frankfurterClient;

    @Test
    void testGetLatestRates() {
       
        FrankfurterResponse mockResponse = new FrankfurterResponse();
        when(restTemplate.getForObject(anyString(), eq(FrankfurterResponse.class)))
                .thenReturn(mockResponse);

        
        FrankfurterResponse result = frankfurterClient.getLatestRates("EUR", List.of("USD", "CZK"));

        
        assertNotNull(result);
    }

    @Test
    void testGetHistoricalRates() {
        
        FrankfurterHistoricalResponse mockResponse = new FrankfurterHistoricalResponse();
        when(restTemplate.getForObject(anyString(), eq(FrankfurterHistoricalResponse.class)))
                .thenReturn(mockResponse);

        
        FrankfurterHistoricalResponse result = frankfurterClient.getHistoricalRates("EUR", "2024-01-01", "2024-01-10", List.of("CZK"));

        
        assertNotNull(result);
    }

    @Test
    void testGetCurrencies() {
        
        Map<String, String> mockCurrencies = Map.of("USD", "United States Dollar", "CZK", "Czech Koruna");
        when(restTemplate.getForObject(anyString(), eq(Map.class)))
                .thenReturn(mockCurrencies);

       
        Map<String, String> result = frankfurterClient.getCurrencies();

        
        assertEquals(2, result.size());
        assertTrue(result.containsKey("CZK"));
    }
}