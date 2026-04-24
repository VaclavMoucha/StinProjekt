package com.currencyapp.backend.service;

import com.currencyapp.backend.model.UserSettings;
import com.currencyapp.backend.repository.SettingsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SettingsServiceTest {

    @Mock
    private SettingsRepository settingsRepository;

    @InjectMocks
    private SettingsService settingsService;

    @Test
    void saveSettings_savesCorrectly() {
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK", "GBP"));

        settingsService.saveSettings(settings);

        verify(settingsRepository).saveSettings(settings); 
    }

    @Test
    void getSettings_returnsCorrectly() {
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK", "GBP"));
        when(settingsRepository.getSettings()).thenReturn(settings);

        UserSettings result = settingsService.getSettings();

        assertEquals("EUR", result.getPreferredCurrency());
        assertEquals(List.of("USD", "CZK", "GBP"), result.getSelectedCurrencies());
    }
}