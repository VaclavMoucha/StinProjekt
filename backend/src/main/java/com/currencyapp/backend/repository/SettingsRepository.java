package com.currencyapp.backend.repository;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.currencyapp.backend.model.UserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class SettingsRepository {
    private static final Logger logger = LoggerFactory.getLogger(SettingsRepository.class);
    @Value("${settings.file.path}")
    private String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserSettings userSettings;

    @PostConstruct
    public void init() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                userSettings = objectMapper.readValue(file, UserSettings.class);
                logger.info("Settings loaded from file: {}", filePath);
            } catch (Exception e) {
                logger.error("Failed to load settings from file: {}", e.getMessage());
                userSettings = null;
            }
        }
    }
    public void saveSettings(UserSettings settings) {
        this.userSettings = settings;
         try {
            objectMapper.writeValue(new File(filePath), settings);
            logger.info("Settings saved to file: {}", filePath);
        } catch (Exception e) {
            logger.error("Failed to save settings: {}", e.getMessage());
            throw new RuntimeException("Failed to save settings", e);
        }
    }

    public UserSettings getSettings() {
        return this.userSettings;
    }
}