package com.example.AskAtEase.controller;

import com.example.AskAtEase.dto.SimilarQuestions;
import com.example.AskAtEase.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class SimilarQuestionController {
    @Autowired
    private final QuestionRepository questionRepository;

    // change ngrok link....
    private final String colabUrl = "https://58c5-34-133-190-139.ngrok-free.app/similar";

    public SimilarQuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/api/search")
    public ResponseEntity<String> getSimilarQuestions(@RequestBody SimilarQuestions similarQuestions) {
        RestTemplate restTemplate = new RestTemplate();

        List<String> allQuestions = questionRepository.findAllQuestions();

        similarQuestions.setQuestions(allQuestions);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SimilarQuestions> request = new HttpEntity<>(similarQuestions, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(colabUrl, request, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
