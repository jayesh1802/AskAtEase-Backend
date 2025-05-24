package com.example.AskAtEase.service;

import com.example.AskAtEase.dto.AnswerDto;

import java.util.List;

public interface AnswerService {
    AnswerDto addAnswerToQuestion(Long queId,AnswerDto answerDto);
    List<AnswerDto> getAllAnswersOfQuestion(Long queId);
}
