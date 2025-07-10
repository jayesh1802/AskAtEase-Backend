package com.example.AskAtEase.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

public interface SummarizeService {
    // we are using async process to optimize the time
    // it is asynchronously called when we hit the sematic search API asynchronously
    @Async
    void generateSummaryAsync(String summaryId, List<String> allAnswerTexts);
    Map<String, String> getSummaryResult(String summaryId);
}
