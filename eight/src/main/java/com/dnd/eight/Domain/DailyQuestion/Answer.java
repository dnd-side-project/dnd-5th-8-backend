package com.dnd.eight.Domain.DailyQuestion;

import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Space.Space;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // answer에 space가 필요한가?  space id로 space찾고, 그 space안에 있는 user리스트 탐색해서, question id와 일치하는 answer들 전부 뽑아오면 될듯
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="space_id")
//    private Space space;


    public void setQuestion(Question question){
//        question.getAnswers().add(this);
        this.question = question;
    }

    public void setUser(User user){
        user.getAnswer().add(this);
        this.user = user;
    }

    public static Answer createAnswer(Question question, User user, String answerContent){
        Answer answer = new Answer();

        answer.setQuestion(question);
        answer.setUser(user);
        answer.setContent(answerContent);

        return answer;
    }
}
