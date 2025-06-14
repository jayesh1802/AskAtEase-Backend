package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.Space;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public QuestionDto mapToQuestionDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setQueId(question.getQueId());
        dto.setQuestion(question.getQuestion());
        dto.setCreatedAt(question.getCreatedAt());

        // Set User ID
        if (question.getUser() != null) {
            dto.setUserId(question.getUser().getUsername());
        }

        // Set Space IDs
        if (question.getSpaces() != null) {
            List<Long> spaceIds = question.getSpaces()
                    .stream()
                    .map(Space::getSpaceId)
                    .collect(Collectors.toList());
            dto.setSpaceIds(spaceIds);
        }

        // Set Answers
        if (question.getAnswers() != null) {
            List<AnswerDto> answerDtos = question.getAnswers()
                    .stream()
                    .map(answer -> new AnswerDto(
                            answer.getAnsId(),
                            answer.getAnswer(),
                            answer.getUser() != null ? answer.getUser().getUsername() : null,
                            answer.getCreatedAt()))
                    .collect(Collectors.toList());
            dto.setAnswers(answerDtos);
        } else {
            dto.setAnswers(Collections.emptyList());
        }

        return dto;
    }

    public Question mapToQuestion(QuestionDto dto) {
        Question question = new Question();
        question.setQueId(dto.getQueId());
        question.setQuestion(dto.getQuestion());
        question.setCreatedAt(dto.getCreatedAt());

        // User, Spaces, and Answers are handled elsewhere (e.g., in Service layer)
        return question;
    }

    public  QuestionWithAnswerDto mapToQuestionWithAnswerDto(Question question) {
        List<AnswerDto> answerDtos = question.getAnswers() != null
                ? question.getAnswers().stream()
                .map(answer -> new AnswerDto(
                        answer.getAnsId(),
                        answer.getAnswer(),
                        answer.getUser() != null ? answer.getUser().getUsername() : null,
                        answer.getCreatedAt()))
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new QuestionWithAnswerDto(
                question.getQueId(),
                question.getQuestion(),
                question.getUser() != null ? question.getUser().getUsername() : null,
                question.getCreatedAt(),
                answerDtos
        );
    }
}
