package com.dnd.eight.Controller.Dto;

import com.dnd.eight.Domain.Login.User;
import lombok.*;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String nickname;
    private String email;
    private String profile;

    @Builder
    public UserRequestDto(String nickname, String email, String profile){
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
    }

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .profile(profile)
                .build();
    }
}
