package com.dnd.eight.Controller.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SpaceResponseDto {
    String spaceName;
    Long userCount;
    Boolean isExist;
}