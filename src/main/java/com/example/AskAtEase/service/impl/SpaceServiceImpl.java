package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.QuestionWithAnswerDto;
import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.Space;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.QuestionMapper;
import com.example.AskAtEase.mapper.SpaceMapper;
import com.example.AskAtEase.repository.QuestionRepository;
import com.example.AskAtEase.repository.SpaceRepository;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final SpaceMapper spaceMapper;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionMapper questionMapper;

    public SpaceServiceImpl(SpaceRepository spaceRepository,
                            SpaceMapper spaceMapper,
                            QuestionRepository questionRepository,
                            UserRepository userRepository,
                            QuestionMapper questionMapper) {
        this.spaceRepository = spaceRepository;
        this.spaceMapper = spaceMapper;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.questionMapper=questionMapper;
    }

    @Override
    public SpaceDto createSpace(SpaceDto spaceDto){
        Space space = spaceMapper.mapToSpace(spaceDto);
        Space savedSpace = spaceRepository.save(space);
        return spaceMapper.mapToSpaceDto(savedSpace);
    }

    @Override
    public List<SpaceDto> getAllSpace(){
        List<Space> spaces = spaceRepository.findAll();
        return spaces.stream()
                .map(spaceMapper::mapToSpaceDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpaceDto getSpaceById(Long spaceId){
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFound("Space does not exist"));
        return spaceMapper.mapToSpaceDto(space);
    }

    @Override
    public void deleteSpaceById(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFound("Space does not exist"));
        spaceRepository.delete(space);
    }
    @Override
    public List<QuestionWithAnswerDto> getQuestionsWithAnswersBySpaceId(Long spaceId){
        Space space=spaceRepository.findById(spaceId)
                .orElseThrow(()->new ResourceNotFound("Space does not exist"));
        List<Question> questions= space.getQuestions();
        return questions.stream()
                .map(questionMapper::mapToQuestionWithAnswerDto)
                .collect(Collectors.toList());

    }
}
