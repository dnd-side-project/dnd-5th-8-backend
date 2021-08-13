package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.UserRequestDto;
import com.dnd.eight.Controller.Dto.UserUpdateDto;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Service.S3FileUploadService;
import com.dnd.eight.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3FileUploadService uploadService;

    @PostMapping("/user")
    public Long createUser(@RequestBody UserRequestDto userDto) {
//        File outputFile = uploadService.imgUrlToFile(userDto.getProfile());
//        userDto.setProfile(uploadService.upload(outputFile, "profile"));
//        outputFile.delete();
        return userService.save(userDto);
    }

    @PutMapping("/user/{id}")
    public Boolean updateUser(@ModelAttribute UserUpdateDto userDto, @PathVariable Long id) throws IOException {
        String deleteProfileName = userService.findProfile(id);
        uploadService.delete(deleteProfileName);

        String profileUrl = uploadService.upload(userDto.getProfile(), "profile");
        return userService.update(id, profileUrl, userDto.getNickname());
    }

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable Long id) {
        User user = userService.findById(id);
        return user.getProfile();
    }
}
