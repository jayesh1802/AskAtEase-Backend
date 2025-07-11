package com.example.AskAtEase.entity;

import com.example.AskAtEase.enums.VoteType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "que_id"}),
        @UniqueConstraint(columnNames = {"user_id", "answer_id"})
})
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    private VoteType vote;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "que_id",referencedColumnName = "que_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "ans_id")
    private Answer answer;


}
