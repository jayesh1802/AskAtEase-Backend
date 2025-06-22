package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import com.example.AskAtEase.entity.User;
import com.example.AskAtEase.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findByUserAndQuestion( Question question,User user);
    Optional<Vote> findByUserAndAnswer(Answer answer, User user);

}
