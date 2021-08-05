package com.dnd.eight.Controller.Dto;

import com.dnd.eight.Domain.Login.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private String nickname;
    private String email;
    private String profile;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .profile(profile)
                .build();
    }
}
