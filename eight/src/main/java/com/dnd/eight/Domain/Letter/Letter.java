package com.dnd.eight.Domain.Letter;

import com.dnd.eight.Domain.DailyQuestion.Question;
import com.dnd.eight.Domain.Login.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_user_id")
    private User to_user; // 받는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_user_id")
    private User from_user; // 보낸 사람

    public void setContent(String content){
        this.content = content;
    }

    public void setTo_user(User to_user){
        this.to_user = to_user;
    }

    public void setFrom_user(User from_user){
        this.from_user = from_user;
    }

    public static Letter saveLetter(String content, User to_user, User from_user) {
        Letter letter = new Letter();

        letter.setContent(content);
        letter.setTo_user(to_user);
        letter.setFrom_user(from_user);

        return letter;
    }
}
