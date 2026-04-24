package com.currencyapp.backend.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrankfurterHistoricalResponse {
    private String base;
    private String start_date;
    private String end_date;
    private Map<String, Map<String, Double>> rates;
}