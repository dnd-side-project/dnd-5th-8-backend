package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.MypageResponseDto;
import com.dnd.eight.Service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/mypage/{user_id}")
    public MypageResponseDto userInfo(@PathVariable long user_id) {
        return mypageService.userInfo(user_id);
    }
}
