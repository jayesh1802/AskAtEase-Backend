package com.example.AskAtEase.controller;

import com.example.AskAtEase.service.SematicService;
import com.example.AskAtEase.service.SummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SematicAndSummarize{

    private final SematicService sematicService;
    private final SummarizeService summarizeService;

    @Autowired
    public SematicAndSummarize(SematicService sematicService,SummarizeService summarizeService) {
        this.sematicService = sematicService;
        this.summarizeService=summarizeService;
    }

    // Endpoint: POST /api/semantic/similar-summary
    @PostMapping("/ask")
    public ResponseEntity<Map<String, Object>> getSimilarQuestions(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        Map<String, Object> result = sematicService.getSimilarQuestionsWithSummary(query);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/summary/{summaryId}")
    public ResponseEntity<Map<String, String>> getSummary(@PathVariable String summaryId) {
        Map<String, String> result = summarizeService.getSummaryResult(summaryId);
        return ResponseEntity.ok(result);
    }
}
