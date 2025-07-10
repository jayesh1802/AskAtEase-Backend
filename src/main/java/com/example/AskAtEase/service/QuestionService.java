package com.example.AskAtEase.service;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.entity.Question;

import java.util.List;

public interface QuestionService {

    // add Question
    QuestionDto addQuestion(QuestionDto questionDto);

    //delete Question, only specific user can delete it
    void deleteQuestion(Long queId);
    List<QuestionDto> getAllQuestions();
    List<QuestionWithAnswerDto> getAllQuestionAns();

}
