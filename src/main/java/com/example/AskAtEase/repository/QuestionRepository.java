package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query("SELECT q.question FROM Question q")
    List<String> findAllQuestions();
}
