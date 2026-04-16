package com.currencyapp.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currencyapp.backend.model.UserSettings;
import com.currencyapp.backend.service.SettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    @GetMapping
    public UserSettings getSettings() {
        return settingsService.getSettings();
    }

    @PostMapping
    public void saveSettings(@RequestBody UserSettings settings) {
        settingsService.saveSettings(settings);
    }

}