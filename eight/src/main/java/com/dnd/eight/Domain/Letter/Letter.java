package com.dnd.eight.Domain.Letter;

import com.dnd.eight.Domain.Login.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_userId")
    private User to_user; // 받는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_userId")
    private User from_user; // 보낸 사람

    public static Letter saveLetter(String content, User to_user, User from_user) {
        Letter letter = new Letter();

        letter.setContent(content);
        letter.setTo_user(to_user);
        letter.setFrom_user(from_user);

        return letter;
    }
}
