package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateResponseDto {
    private String nickName;
    private String profile;
}
