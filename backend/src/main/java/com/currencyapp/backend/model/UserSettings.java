package com.currencyapp.backend.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettings {
    private String preferredCurrency;
    private List<String> selectedCurrencies;
}