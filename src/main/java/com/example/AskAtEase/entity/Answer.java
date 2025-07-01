package com.example.AskAtEase.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ansId"
)
@Entity
@Table(name="answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ans_id")
    private Long ansId;
    @Column(length = 10000)
    private String answer;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name="user_idfk",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="que_idfk",referencedColumnName = "que_id")
    private Question question;

    @Basic
    @Type(JsonType.class)
    @Column(name = "answerEmbedding", columnDefinition = "vector(384)")
    private List<Double> answerEmbedding;

    @OneToMany(mappedBy = "answer",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;

    public void setVotes(List<Vote> votes){
        this.votes=votes;
        for(Vote vote:votes){
            vote.setAnswer(this);
        }
    }





}
