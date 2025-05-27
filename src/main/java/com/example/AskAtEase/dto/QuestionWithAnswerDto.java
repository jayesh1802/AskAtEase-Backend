package com.example.AskAtEase.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class QuestionWithAnswerDto {
    private Long queId;
    private String question;
    private String userId;
    private LocalDateTime createdAt;
    private List<AnswerDto> answers;

    public QuestionWithAnswerDto(Long queId, String question, String userId, LocalDateTime createdAt, List<AnswerDto> answerDtos) {
    }

    public QuestionWithAnswerDto() {

    }
}
