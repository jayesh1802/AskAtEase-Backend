package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}
