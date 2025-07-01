package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.Space;
import com.example.AskAtEase.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class SpaceMapper {
    public SpaceDto mapToSpaceDto(Space space) {
        if (space == null) {
            return null;
        }

        SpaceDto spaceDto = new SpaceDto();
        spaceDto.setSpaceId(space.getSpaceId());
        spaceDto.setSpaceName(space.getSpaceName());
        spaceDto.setDescription(space.getDescription());
        spaceDto.setLocalDateTime(space.getCreatedAt());

        if (space.getUsers() != null) {
            spaceDto.setUserIds(space.getUsers().stream()
                    .map(User::getUsername)

                    .collect(Collectors.toList()));
        }

        if (space.getQuestions() != null) {
            spaceDto.setQueIds(space.getQuestions().stream()
                    .map(Question::getQueId)
                    .collect(Collectors.toList()));
        }

        return spaceDto;
    }

    public Space mapToSpace(SpaceDto spaceDto){
        if (spaceDto == null) {
            return null;
        }

        Space space = new Space();
        space.setSpaceId(spaceDto.getSpaceId());
        space.setSpaceName(spaceDto.getSpaceName());
        space.setDescription(spaceDto.getDescription());
        space.setCreatedAt(spaceDto.getLocalDateTime());
        return space;
        
    }
}
