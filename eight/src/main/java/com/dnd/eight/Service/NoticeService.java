package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.NoticeRequestDto;
import com.dnd.eight.Domain.Notice.Notice;
import com.dnd.eight.Domain.Notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public String saveToken(NoticeRequestDto requestDto) {
        long userId = requestDto.getUserid();
        String token = requestDto.getToken();

        Notice notice = noticeRepository.findByUserid(userId)
                .map(entity -> entity.update(token))
                .orElse(requestDto.toEntity());

        return noticeRepository.save(notice).getToken();
    }
}
