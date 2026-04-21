package com.currencyapp.backend.exception;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleConstraintViolation() {
        ConstraintViolationException ex = new ConstraintViolationException("Validation failed", new HashSet<>());
        String result = handler.handleConstraintViolation(ex);
        assertEquals("Validation failed", result);
    }

    @Test
    void testHandleSettingsNotFound() {
        SettingsNotFoundException ex = new SettingsNotFoundException();
        String result = handler.handleSettingsNotFound(ex);
       assertEquals("Settings not found. Please configure your settings first via POST /api/settings", result);
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError error = new ObjectError("object", "Default error message");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(error));

        String result = handler.handleMethodArgumentNotValid(ex);
        assertEquals("Default error message", result);
    }
}