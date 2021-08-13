package com.dnd.eight.Domain.DailyQuestion;

import com.dnd.eight.Domain.Login.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long space_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void setSpace_id(Long space_id){
        this.space_id = space_id;
    }

    public void setQuestion(Question question){
        this.question = question;
    }

    public void setUser(User user){
        user.getAnswers().add(this);
        this.user = user;
    }

    public static Answer createAnswer(Long id, Question question, User user, String answerContent){
        Answer answer = new Answer();

        answer.setSpace_id(id);
        answer.setQuestion(question);
        answer.setUser(user);
        answer.content = answerContent;

        return answer;
    }
}
