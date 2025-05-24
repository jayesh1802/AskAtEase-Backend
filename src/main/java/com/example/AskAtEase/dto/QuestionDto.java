package com.example.AskAtEase.dto;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionDto {
    private Long queId;
    private String question;
    private LocalDateTime createdAt;
    private UserSummaryDto user;
    private List<AnswerSummaryDto> answers;
}
