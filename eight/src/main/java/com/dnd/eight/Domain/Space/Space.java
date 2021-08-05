package com.dnd.eight.Domain.Space;
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

    @OneToMany(mappedBy = "space", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @Column(length = 500, nullable = false)
    private String code;

    @Column(length = 500, nullable = false)
    private String name;

    private int question_number;
    private int count;

    @Builder
    public Space(String code, String name, int question_number, int count) {
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