package com.example.AskAtEase.repository;

import com.example.AskAtEase.entity.Answer;
import com.example.AskAtEase.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query("SELECT q.question FROM Question q")
    List<String> findAllQuestions();

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answers")
    List<Question> findAllWithAnswers();

    // for performing the sematic Search
    @Query(
            value = "SELECT * FROM question ORDER BY question_embedding <=> CAST(:embedding AS vector) LIMIT :limit",
            nativeQuery = true
    )
    List<Question> findTopSimilarQuestions(@Param("embedding") String embedding, @Param("limit") int limit);


}
