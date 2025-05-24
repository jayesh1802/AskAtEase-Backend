package com.example.AskAtEase.service;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.entity.Question;

import java.util.List;

public interface QuestionService {
    QuestionDto addQuestion(QuestionDto questionDto);
    void deleteQuestion(Long queId);
    List<QuestionDto> getAllQuestions();

}
