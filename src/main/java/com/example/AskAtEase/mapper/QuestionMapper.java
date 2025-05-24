package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionDto mapToQuestionDto(Question question);
    Question mapToQuestion(QuestionDto questionDto);
}
