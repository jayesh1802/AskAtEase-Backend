package com.example.AskAtEase.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// need to learn concept of serialize and infinite recursion.
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
    @Column(name = "que_id")
    Long queId;
    String question;
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_idfk",referencedColumnName = "user_id")
    private User user;

    @Basic
    @Type(JsonType.class)
    @Column(name = "questionEmbedding", columnDefinition = "vector(384)")
    private List<Double> questionEmbedding;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answers=new ArrayList<>();

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    List<Space> spaces =new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public void setAnswers(List<Answer> answers){
        this.answers=answers;
        for(Answer answer:answers){
            answer.setQuestion(this);

        }
    }
    // setting the question in the vote entity..
    public void setVotes(List<Vote> votes){
        this.votes=votes;
        for(Vote vote:votes){
            vote.setQuestion(this);

        }
    }




}
