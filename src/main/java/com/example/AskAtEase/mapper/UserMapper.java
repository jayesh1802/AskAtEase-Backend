package com.example.AskAtEase.mapper;

import com.example.AskAtEase.dto.UserDto;
import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class UserMapper {

    public  UserDto mapToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setBio(user.getBio());
        userDto.setProfilepic(user.getProfilepic());
        userDto.setFollowersCount(user.getFollowersCount());
        userDto.setFollowingCount(user.getFollowingCount());
        userDto.setCreatedAt(user.getCreatedAt());

        if (user.getQuestions() != null) {
            userDto.setQueIds(user.getQuestions().stream()
                    .map(Question::getQueId)
                    .collect(Collectors.toList()));
        }

        if (user.getAnswers() != null) {
            userDto.setAnsIds(user.getAnswers().stream()
                    .map(Answer::getAnsId)
                    .collect(Collectors.toList()));
        }

        return userDto;
    }
    public  User mapToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setBio(userDto.getBio());
        user.setProfilepic(userDto.getProfilepic());
        user.setFollowersCount(userDto.getFollowersCount());
        user.setFollowingCount(userDto.getFollowingCount());
        user.setCreatedAt(userDto.getCreatedAt());


        return user;
    }

}
