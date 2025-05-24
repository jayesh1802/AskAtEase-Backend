package com.example.AskAtEase.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "space")
@Data


public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;
    private String spaceName;
    private String description;
    private LocalDateTime localDateTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="user_space",
            joinColumns = @JoinColumn(name="spaceId"),
            inverseJoinColumns = @JoinColumn(name="userId")
    )
    private List<User> users=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="space_questions",
            joinColumns = @JoinColumn(name="spaceId"),
            inverseJoinColumns = @JoinColumn(name="queId")
    )
    private List<Question> questions=new ArrayList<>();




}
