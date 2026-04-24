package com.currencyapp.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.currencyapp.backend.model.UserSettings;
import com.currencyapp.backend.service.SettingsService;

@ExtendWith(MockitoExtension.class)
public class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;

    @InjectMocks
    private SettingsController settingsController;

    @Test
    void getSettings_returnsSettings() {
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK"));
        when(settingsService.getSettings()).thenReturn(settings);

        UserSettings result = settingsController.getSettings();

        assertEquals("EUR", result.getPreferredCurrency());
    }

    @Test
    void saveSettings_callsService() {
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK"));

        settingsController.saveSettings(settings);

        verify(settingsService).saveSettings(settings);
    }
}