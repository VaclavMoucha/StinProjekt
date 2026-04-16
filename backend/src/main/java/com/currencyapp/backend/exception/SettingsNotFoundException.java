package com.currencyapp.backend.exception;

public class SettingsNotFoundException extends RuntimeException {
    public SettingsNotFoundException() {
        super("Settings not found. Please configure your settings first via POST /api/settings");
    }
}