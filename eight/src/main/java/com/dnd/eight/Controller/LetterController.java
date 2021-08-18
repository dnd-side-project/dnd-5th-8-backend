package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.RecieveLetterRequestDto;
import com.dnd.eight.Controller.Dto.SendLetterRequestDto;
import com.dnd.eight.Controller.Dto.SendLetterResponseDto;
import com.dnd.eight.Service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LetterController {
    private final LetterService letterService;
/*
    @GetMapping("/letter/recieve")
    public LinkedHashMap<String, Object> RecieveLetterList(@RequestBody RecieveLetterRequestDto recieveLetterRequestDto) {
        return letterService.recieveLetterList(recieveLetterRequestDto);
    }
*/
    @GetMapping("/letter/recieve/{userId}/{spaceId}")
    public LinkedHashMap<String, Object> RecieveLetterList(@PathVariable Long userId, @PathVariable Long spaceId) {
        return letterService.recieveLetterList(userId, spaceId);
    }


    @GetMapping("/letter/send/{userId}")
    public List<SendLetterResponseDto> SendLetterList(@PathVariable Long userId) {
        return letterService.sendLetterList(userId);
    }

    @PostMapping("/letter")
    public Long SendLetter(@RequestBody SendLetterRequestDto sendLetterRequestDto) {
        return letterService.saveLetter(sendLetterRequestDto);
    }
}
