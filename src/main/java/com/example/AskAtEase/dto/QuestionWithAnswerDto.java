package com.example.AskAtEase.dto;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionWithAnswerDto {
    private Long queId;
    private String title;
    private String description;
    private String userId;
    private LocalDateTime createdAt;
    private List<AnswerDto> answers;

    public QuestionWithAnswerDto(Long queId, String question, String userId, LocalDateTime createdAt, List<AnswerDto> answerDtos) {
    }
}
