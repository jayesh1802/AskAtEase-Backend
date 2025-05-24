package com.example.AskAtEase.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// need to learn concpt of serialize and infinite recursion.
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "queId"
)
@Data
@Table(name="question")
@Entity
public class Question {
    // learn persistance concept
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long queId;
    String question;
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="userIDFK",referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answers=new ArrayList<>();

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    List<Space> spaces =new ArrayList<>();

    public void setAnswers(List<Answer> answers){
        this.answers=answers;
        for(Answer answer:answers){
            answer.setQuestion(this);

        }

    }

}
