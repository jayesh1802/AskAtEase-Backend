package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.service.SematicService;
import com.example.AskAtEase.service.SummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SematicServiceImpl implements SematicService {
    private final Map<String, String> summaryResults = new ConcurrentHashMap<>();

    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    private final SummarizeService summarizeService;

    private final String EMBEDDING_URL = "http://localhost:8001/embed";

    @Autowired
    public SematicServiceImpl(QuestionRepository questionRepository, RestTemplate restTemplate,SummarizeService summarizeService) {
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
        this.summarizeService=summarizeService;
    }

    @Override
    public Map<String, Object> getSimilarQuestionsWithSummary(String query) {
        // Call FastAPI to get embedding of the query
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

        //convert the List<Embedding> to the string and for performing the sematic search using cosine similarity.

        String embeddingStr = embedding.toString().replaceAll("\\s+", "");

        List<Question> similarQuestions = questionRepository.findTopSimilarQuestions(embeddingStr, 5);

        // we will pass the all AnswersText for performing the text summariation
        List<String> allAnswerTexts = new ArrayList<>();

        // storing similar questions with answers
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
            qMap.put("answers", answerTexts);
            similarWithAnswers.add(qMap);
        }
        String summaryId = UUID.randomUUID().toString();
        // we are calling the summarizeService Asynchronously
        summarizeService.generateSummaryAsync(summaryId, allAnswerTexts);

        // returning back the similar question and similar
        Map<String, Object> result = new HashMap<>();
        result.put("similar", similarWithAnswers);
        result.put("summaryId", summaryId); // <-- Return the ID, not the summary
        return result;
    }

}
