package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.Space;
import com.example.AskAtEase.exception.ResourceNotFound;
import com.example.AskAtEase.mapper.SpaceMapper;
import com.example.AskAtEase.repository.SpaceRepository;
import com.example.AskAtEase.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceServiceImpl implements SpaceService {
    private SpaceRepository spaceRepository;
    private SpaceMapper spaceMapper;
    public SpaceServiceImpl(SpaceRepository spaceRepository){
        this.spaceRepository=spaceRepository;
    }

    @Autowired
    public SpaceServiceImpl(SpaceMapper spaceMapper) {
        this.spaceMapper = spaceMapper;
    }
    @Override
    public SpaceDto createSpace(SpaceDto spaceDto){
        Space space=spaceMapper.mapToSpace(spaceDto);
        Space savedSpace=spaceRepository.save(space);
        return spaceMapper.mapToSpaceDto(savedSpace);
    }
    @Override
    public List<SpaceDto> getAllSpace(){
    List<Space> space=spaceRepository.findAll();
    return space.stream().map((space_it)->spaceMapper.mapToSpaceDto((space_it)))
            .collect(Collectors.toList());
    }
    @Override
    public SpaceDto getSpaceById(Long spaceId){
        Space space= spaceRepository.findById(spaceId)
                .orElseThrow(()-> new ResourceNotFound("Space Does not exist"));
        return spaceMapper.mapToSpaceDto(space);

    }

    @Override
    public void deleteSpaceById(Long spaceId) {
    Space space=spaceRepository.findById(spaceId)
            .orElseThrow(()->new ResourceNotFound("Space does not exist"));
    spaceRepository.deleteById(spaceId);
    }
}
