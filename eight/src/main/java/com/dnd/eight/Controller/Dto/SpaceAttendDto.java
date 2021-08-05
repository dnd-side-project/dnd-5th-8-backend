package com.dnd.eight.Controller.Dto;

import com.dnd.eight.Domain.Space.Space;
import lombok.Getter;

@Getter
public class SpaceAttendDto {
    private long id;
    private String code;

    public SpaceAttendDto(Space entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
    }
}
