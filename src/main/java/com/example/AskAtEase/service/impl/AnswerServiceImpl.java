package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.AnswerMapper;
import com.example.AskAtEase.repository.AnswerRepository;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.service.AnswerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    // why we are supposed to call constructor.

    public  AnswerServiceImpl(AnswerRepository answerRepository,AnswerMapper answerMapper,QuestionRepository questionRepository ,UserRepository userRepository,RestTemplate restTemplate){
        this.answerMapper=answerMapper;
        this.questionRepository=questionRepository;
        this.answerRepository=answerRepository;
        this.userRepository=userRepository;
        this.restTemplate=restTemplate;
    }
    @Override
    public AnswerDto addAnswerToQuestion( AnswerDto answerDto,String userId,Long queId){
        Question question=questionRepository.findById(queId)
                .orElseThrow(()->new ResourceNotFound("question does not exist"));
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFound("user not found"));

        Answer answer=answerMapper.mapToAnswer(answerDto,user,question);

        Answer savedAnswer=answerRepository.save(answer);
        try {
            String url = "http://localhost:8001/embed";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> payload = Map.of("text", savedAnswer.getAnswer());
            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object embeddingObj = response.getBody().get("embedding");
                if (embeddingObj instanceof List<?>) {
                    List<?> rawList = (List<?>) embeddingObj;
                    List<Double> embedding = rawList.stream()
                            .map(o -> ((Number) o).doubleValue())
                            .collect(Collectors.toList());

                    savedAnswer.setAnswerEmbedding(embedding);
                    answerRepository.save(savedAnswer);
                }
            }
        } catch (Exception e) {
            System.err.println("Embedding generation failed: " + e.getMessage());
        }
        return answerMapper.mapToAnswerDto(savedAnswer);
    }
    @Override
    public List<AnswerDto> getAllAnswersOfQuestion(Long queId){
        Question question = questionRepository.findById(queId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        List<Answer> answers=answerRepository.findByQuestion_QueId(queId);
        return answers.stream().map((answer -> answerMapper.mapToAnswerDto(answer))).collect(Collectors.toList());
    }
    @Override
    public QuestionWithAnswerDto getAllAnswersofQuestion(Long queId) {
        Question question = questionRepository.findById(queId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        List<Answer> answers = answerRepository.findByQuestion_QueId(queId);
        List<AnswerDto> answerDtos = answers.stream()
                .map(answerMapper::mapToAnswerDto)
                .collect(Collectors.toList());

        QuestionWithAnswerDto questionWithAnswerDto = new QuestionWithAnswerDto();
        questionWithAnswerDto.setQueId(question.getQueId());
        questionWithAnswerDto.setQuestion(question.getQuestion());
        questionWithAnswerDto.setAnswers(answerDtos);
        questionWithAnswerDto.setCreatedAt(question.getCreatedAt());
        questionWithAnswerDto.setUserId(question.getUser().getUsername());


        return questionWithAnswerDto;
    }


}
