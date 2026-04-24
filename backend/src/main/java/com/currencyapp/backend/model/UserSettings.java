package com.currencyapp.backend.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettings {
    @NotBlank(message = "Preferred currency is required")
    private String preferredCurrency;
    @NotEmpty(message = "Selected currencies cannot be empty")
    private List<String> selectedCurrencies;

}