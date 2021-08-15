package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecieveLetterResponseDto {
    long userId;
    String recieveNickname;
    String content;
}
