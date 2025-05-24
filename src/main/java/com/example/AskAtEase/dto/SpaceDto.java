package com.example.AskAtEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceDto {
    private Long spaceId;
    private String spaceName;
    private String description;
    private LocalDateTime localDateTime;
    private List<UserDto> users;
    private List<QuestionDto> questions;
}
