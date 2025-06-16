package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.Space;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.QuestionMapper;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.repository.SpaceRepository;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            QuestionMapper questionMapper,
            UserRepository userRepository,
            SpaceRepository spaceRepository
    ) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        this.restTemplate = new RestTemplate();     // ✅ initialized
        this.objectMapper = new ObjectMapper();     // ✅ initialized
    }

    @Override
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Question question = questionMapper.mapToQuestion(questionDto);

        // Bind user if present
        if (questionDto.getUserId() != null) {
            User user = userRepository.findById(questionDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFound("User not found with id: " + questionDto.getUserId()));
            question.setUser(user);
        }

        if (questionDto.getSpaceIds() != null && !questionDto.getSpaceIds().isEmpty()) {
            List<Space> spaces = spaceRepository.findAllById(questionDto.getSpaceIds());
            if (spaces.size() != questionDto.getSpaceIds().size()) {
                throw new RuntimeException("Some space IDs are invalid or do not exist.");
            }
            question.setSpaces(spaces);
        }

        Question savedQuestion = questionRepository.save(question);

        // Embedding API call
        try {
            String url = "http://localhost:8001/embed";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> payload = Map.of("text", savedQuestion.getQuestion());

            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object embeddingObj = response.getBody().get("embedding");

                System.out.println("Embedding response: " + embeddingObj);  // Debug print

                if (embeddingObj instanceof List<?>) {
                    List<?> rawList = (List<?>) embeddingObj;

                    // Convert to List<Double>
                    List<Double> embedding = rawList.stream()
                            .map(o -> ((Number) o).doubleValue())
                            .collect(Collectors.toList());

                    savedQuestion.setQuestionEmbedding(embedding);
                    savedQuestion = questionRepository.save(savedQuestion); // Save again with embedding
                } else {
                    System.err.println("Unexpected embedding format: " + embeddingObj);
                }
            }
        } catch (Exception e) {
            System.err.println("Embedding generation failed: " + e.getMessage());
        }

        return questionMapper.mapToQuestionDto(savedQuestion);
    }


    @Override
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(questionMapper::mapToQuestionDto).collect(Collectors.toList());
    }

    @Override
    public void deleteQuestion(Long queId) {
        Question question = questionRepository.findById(queId)
                .orElseThrow(() -> new ResourceNotFound("Question does not exist"));
        questionRepository.deleteById(queId);
    }

    @Override
    public List<QuestionWithAnswerDto> getAllQuestionAns() {
        List<Question> queAns = questionRepository.findAllWithAnswers();
        return queAns.stream()
                .map(questionMapper::mapToQuestionWithAnswerDto)
                .collect(Collectors.toList());
    }
}
