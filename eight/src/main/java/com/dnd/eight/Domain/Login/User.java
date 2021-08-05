package com.dnd.eight.Domain.Login;
import com.dnd.eight.Domain.Space.Space;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

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

