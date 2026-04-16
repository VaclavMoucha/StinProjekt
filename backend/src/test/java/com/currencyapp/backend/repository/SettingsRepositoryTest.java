package com.currencyapp.backend.repository;

import com.currencyapp.backend.model.UserSettings;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SettingsRepositoryTest {

    private SettingsRepository settingsRepository;

    @BeforeEach
    void setUp() throws Exception {
        settingsRepository = new SettingsRepository();

        java.lang.reflect.Field field = SettingsRepository.class.getDeclaredField("filePath");
        field.setAccessible(true);
        field.set(settingsRepository, "test-settings.json");
    }

    @AfterEach
    void tearDown() {

        new java.io.File("test-settings.json").delete();
    }

    @Test
    void getSettings_returnsNull_whenNoFile() {
        settingsRepository.init();
        assertNull(settingsRepository.getSettings());
    }

    @Test
    void saveSettings_savesAndReturns() {
        settingsRepository.init();
        UserSettings settings = new UserSettings("EUR", List.of("USD", "CZK"));

        settingsRepository.saveSettings(settings);

        assertEquals("EUR", settingsRepository.getSettings().getPreferredCurrency());
    }

    @Test
    void init_loadsSettings_whenFileExists() {

        settingsRepository.init();
        UserSettings settings = new UserSettings("USD", List.of("CZK", "GBP"));
        settingsRepository.saveSettings(settings);


        SettingsRepository newRepository = new SettingsRepository();
        try {
            java.lang.reflect.Field field = SettingsRepository.class.getDeclaredField("filePath");
            field.setAccessible(true);
            field.set(newRepository, "test-settings.json");
        } catch (Exception e) {
            fail("Failed to set field");
        }
        newRepository.init();

        assertEquals("USD", newRepository.getSettings().getPreferredCurrency());
    }
}