package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponseDto {
    String user_profile;
    String user_nickName;
    String content;
    String emoji;
    private LocalDateTime createdDate;
}
