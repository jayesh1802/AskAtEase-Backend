package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.AnswerMapper;
import com.example.AskAtEase.mapper.QuestionMapper;
import com.example.AskAtEase.repository.AnswerRepository;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.service.AnswerService;

import java.util.List;
import java.util.stream.Collectors;

public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final QuestionRepository questionRepository;
    // why we are supposed to call constructor.
    public  AnswerServiceImpl(AnswerRepository answerRepository,AnswerMapper answerMapper,QuestionRepository questionRepository){
        this.answerMapper=answerMapper;
        this.questionRepository=questionRepository;
        this.answerRepository=answerRepository;
    }
    @Override
    public AnswerDto addAnswerToQuestion(Long queId, AnswerDto answerDto){
        Question question=questionRepository.findById(queId)
                .orElseThrow(()->new ResourceNotFound("question does not exist"));
        Answer answer=answerMapper.mapToAnswer(answerDto);
        answer.setQuestion(question);
        Answer savedAnswer=answerRepository.save(answer);
        return answerMapper.mapToAnswerDto(savedAnswer);
    }
    @Override
    public List<AnswerDto> getAllAnswersOfQuestion(Long queId){
        Question question = questionRepository.findById(queId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        List<Answer> answers=answerRepository.findByQuestion_QueId(queId);
        return answers.stream().map((answer -> answerMapper.mapToAnswerDto(answer))).collect(Collectors.toList());
    }
}
