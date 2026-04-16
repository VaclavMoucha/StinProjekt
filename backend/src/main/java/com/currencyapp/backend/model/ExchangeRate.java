package com.currencyapp.backend.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
    private String currency;
    private double rate;
    String date;
}