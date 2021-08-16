package com.dnd.eight.Domain.Notice;

import com.dnd.eight.Domain.Login.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @Column(nullable = false)
    private Long userid;

    @Column(nullable = false)
    private String token;

    @Builder
    public Notice(Long userid, String token) {
        this.userid = userid;
        this.token = token;
    }

    public Notice update(String token){
        this.token = token;

        return this;
    }
}
