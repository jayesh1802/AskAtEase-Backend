package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.SpaceDto;
import com.example.AskAtEase.entity.Space;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

    SpaceDto mapToSpaceDto(Space space);
    Space mapToSpace(SpaceDto spaceDto);
}
