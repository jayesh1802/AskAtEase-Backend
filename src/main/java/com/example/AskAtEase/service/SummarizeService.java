package com.example.AskAtEase.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

public interface SummarizeService {
    @Async
    void generateSummaryAsync(String summaryId, List<String> allAnswerTexts);
    Map<String, String> getSummaryResult(String summaryId);
}
