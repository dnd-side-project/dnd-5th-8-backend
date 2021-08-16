package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.NoticeRequestDto;
import com.dnd.eight.Service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/notice/token")
    public String saveToken(@RequestBody NoticeRequestDto noticeRequestDto) {
        return noticeService.saveToken(noticeRequestDto);
    }
}
