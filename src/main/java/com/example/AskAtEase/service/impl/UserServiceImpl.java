package com.example.AskAtEase.service.impl;

import com.example.AskAtEase.repository.UserRepository;
import com.example.AskAtEase.service.UserService;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
