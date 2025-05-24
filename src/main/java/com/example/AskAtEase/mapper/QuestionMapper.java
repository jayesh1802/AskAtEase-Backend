package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {

    public QuestionDto mapToQuestionDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setQueId(question.getQueId());
        dto.setQuestion(question.getQuestion());
        dto.setCreatedAt(question.getCreatedAt());

        if (question.getUser() != null) {
            dto.setUserId(question.getUser().getUserId());
        }

        if (question.getAnswers() != null) {
            List<AnswerDto> answerDtos = new ArrayList<>();
            for (Answer answer : question.getAnswers()) {
                AnswerDto answerDto = new AnswerDto();
                answerDto.setAnsId(answer.getAnsId());
                answerDto.setAnswer(answer.getAnswer());
                answerDto.setCreatedAt(answer.getCreatedAt());
                answerDtos.add(answerDto);
            }
            dto.setAnswers(answerDtos);
        }

        return dto;
    }

    public Question mapToQuestion(QuestionDto dto) {
        Question question = new Question();
        question.setQueId(dto.getQueId());
        question.setQuestion(dto.getQuestion());
        question.setCreatedAt(dto.getCreatedAt());

        // User will be set separately in the service (if needed)
        // Same with answers

        return question;
    }
}
