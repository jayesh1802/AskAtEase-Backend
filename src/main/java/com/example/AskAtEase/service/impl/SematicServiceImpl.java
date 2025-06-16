package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.service.SematicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SematicServiceImpl implements SematicService {

    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;

    private final String EMBEDDING_URL = "http://localhost:8001/embed";
    private final String SUMMARIZE_URL = "http://localhost:8002/summarize";

    @Autowired
    public SematicServiceImpl(QuestionRepository questionRepository, RestTemplate restTemplate) {
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Object> getSimilarQuestionsWithSummary(String query) {
        // Step 1: Call FastAPI to get embedding of the query
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> embedPayload = Map.of("text", query);
        HttpEntity<Map<String, String>> embedRequest = new HttpEntity<>(embedPayload, headers);

        ResponseEntity<Map> embedResponse;
        try {
            embedResponse = restTemplate.postForEntity(EMBEDDING_URL, embedRequest, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to embedding service");
        }

        if (!embedResponse.getStatusCode().is2xxSuccessful() || embedResponse.getBody() == null) {
            throw new RuntimeException("Invalid response from embedding service");
        }

        List<Double> embedding = ((List<?>) embedResponse.getBody().get("embedding"))
                .stream()
                .map(val -> ((Number) val).doubleValue())
                .collect(Collectors.toList());

        String embeddingStr = embedding.toString().replaceAll("\\s+", "");

        List<Question> similarQuestions = questionRepository.findTopSimilarQuestions(embeddingStr, 5);

        List<String> allAnswerTexts = new ArrayList<>();
        List<Map<String, Object>> similarWithAnswers = new ArrayList<>();

        for (Question q : similarQuestions) {
            List<Answer> answerList = q.getAnswers();

            List<String> answerTexts = answerList.stream()
                    .map(Answer::getAnswer)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(ans -> !ans.isEmpty())
                    .collect(Collectors.toList());

            allAnswerTexts.addAll(answerTexts);

            Map<String, Object> qMap = new HashMap<>();
            qMap.put("id", q.getQueId());
            qMap.put("question", q.getQuestion());
            qMap.put("answers", answerTexts); // Optional: for frontend display
            similarWithAnswers.add(qMap);
        }

        Map<String, Object> summarizePayload = Map.of("answers", allAnswerTexts);
        HttpEntity<Map<String, Object>> summarizeRequest = new HttpEntity<>(summarizePayload, headers);

        String summary;
        try {
            ResponseEntity<Map> summarizeResponse = restTemplate.postForEntity(SUMMARIZE_URL, summarizeRequest, Map.class);
            summary = (summarizeResponse.getStatusCode() == HttpStatus.OK && summarizeResponse.getBody() != null)
                    ? (String) summarizeResponse.getBody().get("summary")
                    : "Summary service returned no result.";
        } catch (Exception e) {
            summary = "Failed to connect to summary service.";
        }

        // Step 6: Construct response
        Map<String, Object> result = new HashMap<>();
        result.put("similar", similarWithAnswers);
        result.put("summary", summary);
        return result;
    }
}
