package com.example.AskAtEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long queId;
    private String question;
    private LocalDateTime createdAt;
//    private UserDto user;
    private String userId;
    private List<AnswerDto> answers;

}
