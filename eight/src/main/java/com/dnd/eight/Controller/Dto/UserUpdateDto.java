package com.dnd.eight.Controller.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UserUpdateDto {
    private String nickname;
    private MultipartFile profile;
}
