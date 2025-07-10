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
    // for Adding answer to question, userId and queId required
    AnswerDto addAnswerToQuestion(AnswerDto answerDto,String userId,Long queId);

    //Retrieve all the Answers of Questions.
    List<AnswerDto> getAllAnswersOfQuestion(Long queId);

    //For APIs in which question and answer both are required, so combining them for DTO
    QuestionWithAnswerDto getAllAnswersofQuestion(Long queId);
}
