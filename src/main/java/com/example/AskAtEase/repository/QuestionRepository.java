package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
