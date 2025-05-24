package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerDto mapToAnswerDto(Answer answer);
    Answer mapToAnswer(AnswerDto answerDto);
}
