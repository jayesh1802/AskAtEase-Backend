package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.QuestionMapper;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private  final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    public QuestionServiceImpl(QuestionRepository questionRepository,QuestionMapper questionMapper ){
        this.questionRepository=questionRepository;
        this.questionMapper=questionMapper;
    }

    @Override
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Question question=questionMapper.mapToQuestion(questionDto);
        Question savedQuestion=questionRepository.save(question);
        return questionMapper.mapToQuestionDto(savedQuestion);
    }
    @Override
    public List<QuestionDto> getAllQuestions(){
        List<Question> questions=questionRepository.findAll();
        return questions.stream().map((question -> questionMapper.mapToQuestionDto(question)))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteQuestion(Long queId){
        Question question=questionRepository.findById(queId)
                .orElseThrow(()-> new ResourceNotFound("Question does not exist"));
        questionRepository.deleteById(queId);


    }
}
