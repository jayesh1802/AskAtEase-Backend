package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.UserDto;
import com.example.AskAtEase.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToUserDto(User user);
    User mapToUser(UserDto userDto);

}
