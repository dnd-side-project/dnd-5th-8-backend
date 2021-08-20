package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendLetterResponseDto {
    String toNickname;
    String toProfile;
    String content;
}
