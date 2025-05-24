package com.example.AskAtEase.service;

import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceService {
    SpaceDto createSpace(SpaceDto spaceDto);
    List<SpaceDto> getAllSpace();
    SpaceDto getSpaceById(Long spaceId);
    void deleteSpaceById(Long spaceId);
}
