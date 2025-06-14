package com.example.AskAtEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWithAnswerDto {
    private Long queId;
    private String question;
    private String userId;
    private LocalDateTime createdAt;
    private List<AnswerDto> answers;
}
