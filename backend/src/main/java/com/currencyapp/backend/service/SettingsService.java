package com.currencyapp.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.currencyapp.backend.repository.SettingsRepository;
import com.currencyapp.backend.model.UserSettings;

@Service

public class SettingsService {
    @Autowired
    private SettingsRepository settingsRepository;

    public void saveSettings(UserSettings settings) {
        settingsRepository.saveSettings(settings);
    }

    public UserSettings getSettings() {
        return settingsRepository.getSettings();
    }
}