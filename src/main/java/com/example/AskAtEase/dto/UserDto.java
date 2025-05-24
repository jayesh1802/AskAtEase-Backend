package com.example.AskAtEase.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String bio;
    private String profilepic;
    private int followersCount;
    private int followingCount;
    private LocalDateTime createdAt;
    private List<QuestionSummaryDto> questions;
    private List<AnswerSummaryDto> answers;
}
