package com.dnd.eight.Controller.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyTalkRoundMessage {
    private Long spaceId;
    private Long userId;
    private Integer round;
}
