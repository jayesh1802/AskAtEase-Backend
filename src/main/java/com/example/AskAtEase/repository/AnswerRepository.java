package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQuestion_QueId(Long queId);
}
