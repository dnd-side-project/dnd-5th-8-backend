package com.dnd.eight.Controller.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyTalkJoinMessage {
    private Long spaceId;
    private Long userId;
}
