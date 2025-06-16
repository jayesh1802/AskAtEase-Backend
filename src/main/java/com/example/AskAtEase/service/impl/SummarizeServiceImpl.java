package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.service.SummarizeService;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SummarizeServiceImpl implements SummarizeService {

    // Move the summary results map here
    private final Map<String, String> summaryResults = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;
    private final String SUMMARIZE_URL = "http://localhost:8002/summarize";

    public SummarizeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void generateSummaryAsync(String summaryId, List<String> allAnswerTexts) {
        summaryResults.put(summaryId, "pending");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> summarizePayload = Map.of("answers", allAnswerTexts);
        HttpEntity<Map<String, Object>> summarizeRequest = new HttpEntity<>(summarizePayload, headers);

        String summary;
        try {
            ResponseEntity<Map> summarizeResponse = restTemplate.postForEntity(SUMMARIZE_URL, summarizeRequest, Map.class);
            summary = (summarizeResponse.getStatusCode() == HttpStatus.OK && summarizeResponse.getBody() != null)
                    ? (String) summarizeResponse.getBody().get("summary")
                    : "Summary service returned no result.";
        } catch (Exception e) {
            summary = "Error: Failed to connect to summary service.";
        }

        summaryResults.put(summaryId, summary);
    }

    public Map<String, String> getSummaryResult(String summaryId) {
        String summary = summaryResults.getOrDefault(summaryId, "not_found");
        String status = "pending";

        if (!summary.equals("pending") && !summary.equals("not_found")) {
            status = "completed";
        } else if (summary.equals("not_found")) {
            status = "error";
            summary = "Invalid summary ID.";
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        response.put("summary", status.equals("completed") ? summary : "");

        if (status.equals("completed") || status.equals("error")) {
            summaryResults.remove(summaryId);
        }

        return response;
    }
}