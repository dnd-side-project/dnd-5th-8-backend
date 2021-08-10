package com.dnd.eight.Domain.DailyQuestion;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false)
    private String content;

//    @OneToMany(mappedBy = "question")
//    private List<Answer> answers = new ArrayList<>();
//
//    @OneToMany(mappedBy = "question")
//    private List<Comment> comments = new ArrayList<>();
}
