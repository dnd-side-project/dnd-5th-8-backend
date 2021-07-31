package com.dnd.eight.Domain.Space;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="Space")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String code;

    private int question_number;
    private int count;

    @Builder
    public Space(String code, int question_number, int count) {
        this.code = code;
        this.question_number = question_number;
        this.count = count;
    }
}
