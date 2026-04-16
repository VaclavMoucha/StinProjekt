package com.currencyapp.backend.repository;

import org.springframework.stereotype.Component;
import com.currencyapp.backend.model.UserSettings;

@Component
public class SettingsRepository {
    private UserSettings userSettings;

    public void saveSettings(UserSettings settings) {
        this.userSettings = settings;
    }

    public UserSettings getSettings() {
        return this.userSettings;
    }
}