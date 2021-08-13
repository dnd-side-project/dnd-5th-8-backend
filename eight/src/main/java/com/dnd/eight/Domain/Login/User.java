package com.dnd.eight.Domain.Login;
import com.dnd.eight.Domain.DailyQuestion.Answer;
import com.dnd.eight.Domain.DailyQuestion.Comment;
import com.dnd.eight.Domain.Space.Space;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToMany(mappedBy = "user")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

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

