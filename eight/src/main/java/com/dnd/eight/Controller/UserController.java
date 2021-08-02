package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.UserRequestDto;
import com.dnd.eight.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public Boolean createUser(@RequestBody UserRequestDto userDto){
//        System.out.println(userDto.getNickname() + " " + userDto.getEmail() + " " + userDto.getProfile());
        return userService.save(userDto);
    }
}
