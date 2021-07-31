package com.dnd.eight.Controller.Dto;

import com.dnd.eight.Domain.Space.Space;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SpaceResponseDto {
    private String code;
    private int question_number;
    private int count;

    @Builder
    public SpaceResponseDto(String code, int question_number, int count) {
        this.code = code;
        this.question_number = question_number;
        this.count = count;
    }
}
