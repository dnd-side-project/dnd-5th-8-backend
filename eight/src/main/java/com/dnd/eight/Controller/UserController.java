package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.UserRequestDto;
import com.dnd.eight.Controller.Dto.UserUpdateDto;
import com.dnd.eight.Controller.Dto.UserUpdateResponseDto;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public Long createUser(@RequestBody UserRequestDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/user/{id}")
    public UserUpdateResponseDto updateUser(@ModelAttribute UserUpdateDto userDto, @PathVariable Long id) throws IOException {
        if(userDto.getProfile() == null){
            //nickname
            return userService.update(id, userDto.getNickname());
        }

        if(userDto.getNickname() == null){
            //profile
            return userService.update(id, userDto.getProfile());
        }

        return userService.update(id, userDto.getNickname(), userDto.getProfile());
    }

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable Long id) {
        User user = userService.findById(id);
        return user.getProfile();
    }
}
