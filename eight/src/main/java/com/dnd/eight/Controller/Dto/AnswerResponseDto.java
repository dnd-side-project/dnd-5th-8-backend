package com.dnd.eight.Controller.Dto;

import com.dnd.eight.Domain.Login.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
public class AnswerResponseDto {
    String user_nickName;
    String content;
}
