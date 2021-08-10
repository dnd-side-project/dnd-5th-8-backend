package com.dnd.eight.Domain.Space;
import com.dnd.eight.Domain.DailyQuestion.Answer;
import com.dnd.eight.Domain.DailyQuestion.Comment;
import com.dnd.eight.Domain.Login.User;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="space")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String code;

    @Column(length = 500, nullable = false)
    private String name;

    private Long question_number;
    private int count;

    @OneToMany(mappedBy = "space")
    private List<User> users = new ArrayList<>();

//    @OneToMany(mappedBy = "space")
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "space")
//    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Space(String code, String name, Long question_number, int count) {
        this.code = code;
        this.name = name;
        this.question_number = question_number;
        this.count = count;
    }

    public void addUser(User user){
        users.add(user);
        user.setSpace(this);
    }
}