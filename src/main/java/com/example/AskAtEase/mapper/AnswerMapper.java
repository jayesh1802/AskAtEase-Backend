package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.AnswerDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {
    public AnswerDto mapToAnswerDto(Answer answer){
        AnswerDto answerDto= new AnswerDto();
        answerDto.setAnsId(answer.getAnsId());
        answerDto.setAnswer(answer.getAnswer());
        answerDto.setCreatedAt(answer.getCreatedAt());
        answerDto.setUserId(answer.getUser().getUsername());
        answerDto.setQueId(answer.getQuestion()!=null? answer.getQuestion().getQueId():null);
        return answerDto;
    }
    public Answer mapToAnswer(AnswerDto answerDto, User user, Question question){
        if(answerDto==null) return null;
        Answer answer= new Answer();
        answer.setAnsId(answerDto.getAnsId());
        answer.setAnswer(answerDto.getAnswer());
        answer.setCreatedAt(answerDto.getCreatedAt());
        answer.setUser(user);
        answer.setQuestion(question);
        return answer;
    }
}
