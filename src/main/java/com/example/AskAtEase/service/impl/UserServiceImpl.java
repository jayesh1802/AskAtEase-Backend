package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.dto.UserDto;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.mapper.UserMapper;
import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.service.UserService;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto){
        User user1=userMapper.mapToUser(userDto);
        User savedUser=userRepository.save(user1);
        return userMapper.mapToUserDto(savedUser);
    }

}
