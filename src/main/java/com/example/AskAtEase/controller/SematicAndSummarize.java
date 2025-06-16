package com.example.AskAtEase.controller;

import com.example.AskAtEase.service.SematicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SematicAndSummarize{

    private final SematicService sematicService;

    @Autowired
    public SematicAndSummarize(SematicService sematicService) {
        this.sematicService = sematicService;
    }

    // Endpoint: POST /api/semantic/similar-summary
    @PostMapping()
    public ResponseEntity<Map<String, Object>> getSimilarWithSummary(@RequestBody Map<String, String> request) {
        String query = request.get("query");

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Query text is required."));
        }

        Map<String, Object> response = sematicService.getSimilarQuestionsWithSummary(query);
        return ResponseEntity.ok(response);
    }
}
