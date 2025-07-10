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
// to avoid infinite loop
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "ansId"
)
// answer Entity
@Entity
@Table(name="answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ans_id")
    private Long ansId;

    // keep string of max length to store in postgre and if more needed later, we can add
    @Column(length = 10000)
    private String answer;
    private LocalDateTime createdAt;

    // Many to One mapping of answer to user and user
    // use snake_case for column name as it is standard in postgres to use snake_case and by default it stores it
    @ManyToOne
    @JoinColumn(name="user_idfk",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="que_idfk",referencedColumnName = "que_id")
    private Question question;

    //To store the embedding to perform the NLP tasks
    //https://stackoverflow.com/questions/76553746/what-jpa-hibernate-data-type-should-i-use-to-support-the-vector-extension-in-a
    @Basic
    // serialize the List<Double> to the Json
    @Type(JsonType.class)
    @Column(name = "answerEmbedding", columnDefinition = "vector(384)")
    private List<Double> answerEmbedding;

    @OneToMany(mappedBy = "answer",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;
    // to set the answer in the vote entity..
    public void setVotes(List<Vote> votes){
        this.votes=votes;
        for(Vote vote:votes){
            vote.setAnswer(this);
        }
    }





}
