package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.QuestionDto;
import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.Space;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.QuestionMapper;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.repository.SpaceRepository;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            QuestionMapper questionMapper,
            UserRepository userRepository,
            SpaceRepository spaceRepository
    ) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.userRepository = userRepository;
        this.spaceRepository=spaceRepository;
    }

    @Override
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Question question = questionMapper.mapToQuestion(questionDto);

        // Optional user binding
        if (questionDto.getUserId() != null) {
            User user = userRepository.findById(questionDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFound("User not found with id: " + questionDto.getUserId()));
            question.setUser(user);
        } else {
            question.setUser(null);
        }
        if (questionDto.getSpaceIds() != null && !questionDto.getSpaceIds().isEmpty()) {
            List<Space> spaces = spaceRepository.findAllById(questionDto.getSpaceIds());

            if (spaces.size() != questionDto.getSpaceIds().size()) {
                throw new RuntimeException("Some space IDs are invalid or do not exist.");
            }

            question.setSpaces(spaces);
        }


        // Save question and return DTO
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.mapToQuestionDto(savedQuestion);
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map((question -> questionMapper.mapToQuestionDto(question))).collect(Collectors.toList());
    }
    @Override
    public void deleteQuestion(Long queId) {
        Question question = questionRepository.findById(queId)
                .orElseThrow(() -> new ResourceNotFound("Question does not exist"));
        questionRepository.deleteById(queId);
    }
    @Override
    public List<QuestionWithAnswerDto> getAllQuestionAns(){
        List<Question> queAns=questionRepository.findAllWithAnswers();
        return queAns.stream()
                .map(questionMapper::mapToQuestionWithAnswerDto)
                .collect(Collectors.toList());
    }
}
