package com.dnd.eight.Domain.Space;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="space")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
