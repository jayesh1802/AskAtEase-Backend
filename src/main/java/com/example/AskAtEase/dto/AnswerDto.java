package com.example.AskAtEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long ansId;
    private String answer;
    private LocalDateTime createdAt;
    private UserDto user;
    private String userId;
    private Long queId;
    private QuestionDto question;

    public AnswerDto(Long ansId, String answer, String userId, LocalDateTime createdAt) {
    }
}
