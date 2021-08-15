package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendLetterRequestDto {
    private Long touserId;
    private Long fromuserId;
    private String content;
}
