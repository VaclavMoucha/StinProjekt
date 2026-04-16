package com.currencyapp.backend.repository;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import com.currencyapp.backend.model.LogEntry;

@Component
public class LogRepository {
    private List<LogEntry> logs = new ArrayList<>();

    public void addLog(LogEntry log) {
        logs.add(log);
    }

    public List<LogEntry> getLogs() {
        return logs;
    }
}