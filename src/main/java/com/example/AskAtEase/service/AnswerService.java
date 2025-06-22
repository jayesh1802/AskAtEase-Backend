package com.example.AskAtEase.service;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;

import java.util.List;

public interface AnswerService {
    /**
     *
     * @param answerDto
     * @param userId
     * @param queId
     * @return
     */
    AnswerDto addAnswerToQuestion(AnswerDto answerDto,String userId,Long queId);
    List<AnswerDto> getAllAnswersOfQuestion(Long queId);
    QuestionWithAnswerDto getAllAnswersofQuestion(Long queId);
}
