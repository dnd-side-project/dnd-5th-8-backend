package com.dnd.eight.Controller.Dto;

import com.dnd.eight.Domain.Notice.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeRequestDto {
    private Long userid;
    private String token;

    public Notice toEntity(){
        return Notice.builder()
                .userid(userid)
                .token(token)
                .build();
    }
}
