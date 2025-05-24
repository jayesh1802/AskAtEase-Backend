package com.example.AskAtEase.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId"
)

public class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String bio;
    private String profilepic;
    private int followersCount;
    private int followingCount;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    List<Space> spaces = new ArrayList<>();

    public void setQuestions(List<Question> questions){
        this.questions=questions;
        for(Question question: questions){
            question.setUser(this);
        }
    }
    public void setAnswers(List<Answer> answers){
        this.answers=answers;
        for(Answer answer:answers){
            answer.setUser(this);
        }
    }

}
