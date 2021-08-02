package com.dnd.eight.Domain.Login;

import lombok.*;
import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String profile;

    @Builder
    public User(String nickname, String email, String profile) {
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
    }

    public User update(String nickname, String profile){
        this.nickname = nickname;
        this.profile = profile;

        return this;
    }
}
