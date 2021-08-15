package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyResponseDto {
    Long userId;
    String nickname;
    String profile;
}
