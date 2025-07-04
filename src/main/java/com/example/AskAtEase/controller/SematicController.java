package com.example.AskAtEase.controller;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SematicController {

    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;

    private final String SIMILAR_URL = "http://127.0.0.1:8006/similar";
    private final String SUMMARIZE_URL = "http://127.0.0.1:8006/summarize";

    @Autowired
    public SematicController(QuestionRepository questionRepository, RestTemplate restTemplate) {
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/api/similar-summary")
    public ResponseEntity<?> getSimilarQuestionsAndSummary(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Query cannot be empty.");
        }

        List<Question> allQuestions = questionRepository.findAllWithAnswers();

        Map<String, Question> questionTextToQuestionMap = new HashMap<>();
        List<String> questionTexts = new ArrayList<>(allQuestions.size());

        for (Question q : allQuestions) {
            questionTexts.add(q.getQuestion());
            questionTextToQuestionMap.put(q.getQuestion(), q);
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("questions", questionTexts);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.postForEntity(SIMILAR_URL, requestEntity, Map.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get similar questions from external service.");
        }

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return ResponseEntity.status(response.getStatusCode())
                    .body("Invalid response from similarity service.");
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody().get("results");
        if (results == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Map<String, Object>> similarWithAnswers = new ArrayList<>(results.size());
        List<String> allAnswers = new ArrayList<>();

        for (Map<String, Object> result : results) {
            String questionText = (String) result.get("question");
            Double score = (Double) result.get("score");

            Question matchedQuestion = questionTextToQuestionMap.get(questionText);

            if (matchedQuestion == null) {
                System.out.println("[DEBUG] No match for question: " + questionText);
            } else {
                System.out.println("[DEBUG] Matched Question ID: " + matchedQuestion.getQueId());
                System.out.println("[DEBUG] Matched Question Text: " + matchedQuestion.getQuestion());
                System.out.println("[DEBUG] Total answers linked: " + matchedQuestion.getAnswers().size());

                List<String> answers = matchedQuestion.getAnswers().stream()
                        .map(Answer::getAnswer)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                System.out.println("[DEBUG] Non-null answer count: " + answers.size());
                for (String ans : answers) {
                    System.out.println("  ↳ " + ans);
                }

                allAnswers.addAll(answers);

                Map<String, Object> entry = new HashMap<>();
                entry.put("id", matchedQuestion.getQueId());
                entry.put("question", questionText);
                entry.put("score", score);
                entry.put("answers", answers);
                similarWithAnswers.add(entry);
            }
        }

        // Print all answers collected before summarization
        System.out.println("[DEBUG] Total answers collected for summarization: " + allAnswers.size());
        for (String ans : allAnswers) {
            System.out.println("  ↳ " + ans);
        }

        // Now call the summarize endpoint
        Map<String, Object> summarizeRequest = new HashMap<>();
        summarizeRequest.put("answers", allAnswers);

        HttpEntity<Map<String, Object>> summarizeEntity = new HttpEntity<>(summarizeRequest, headers);
        String summary;
        try {
            ResponseEntity<Map> summarizeResponse = restTemplate.postForEntity(SUMMARIZE_URL, summarizeEntity, Map.class);
            if (summarizeResponse.getStatusCode() == HttpStatus.OK && summarizeResponse.getBody() != null) {
                summary = (String) summarizeResponse.getBody().get("summary");
                System.out.println("[DEBUG] Summary received: " + summary);
            } else {
                summary = "Summary service failed to respond.";
                System.out.println("[DEBUG] Summarization failed: invalid response");
            }
        } catch (Exception e) {
            summary = "Failed to summarize answers.";
            System.out.println("[DEBUG] Summarization failed: exception - " + e.getMessage());
        }

        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("similar", similarWithAnswers);
        finalResponse.put("summary", summary);

        return ResponseEntity.ok(finalResponse);
    }
}
