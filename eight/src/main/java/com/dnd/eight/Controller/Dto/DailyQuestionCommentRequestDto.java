package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class DailyQuestionCommentRequestDto {
    String content;
    String emoji;
    Long userId;
}
