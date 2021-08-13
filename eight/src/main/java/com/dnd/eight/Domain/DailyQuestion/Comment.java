package com.dnd.eight.Domain.DailyQuestion;

import com.dnd.eight.Domain.BaseTimeEntity;
import com.dnd.eight.Domain.Login.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void setQuestion(Question question){
        this.question = question;
    }

    public void setUser(User user){
        user.getComments().add(this);
        this.user = user;
    }

    public static Comment createComment(Question question, User user, String commentContent, String emoji){
        Comment comment = new Comment();

        comment.setQuestion(question);
        comment.setUser(user);
        comment.content = commentContent;
        comment.emoji = emoji;

        return comment;
    }
}
