package com.example.AskAtEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String bio;
    private String profilepic;
    private int followersCount;
    private int followingCount;
    private LocalDateTime createdAt;
    private List<Long> queIds;
    private List<Long> ansIds;
    private List<Long> spaceIds;
//    private List<QuestionDto> questions;
//    private List<AnswerDto> answers;
}
