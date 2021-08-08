package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.MypageResponseDto;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import com.dnd.eight.Domain.Space.Space;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final UserRepository userRepository;

    public MypageResponseDto userInfo(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+user_id));
        Space space = user.getSpace();

        MypageResponseDto responseDto = new MypageResponseDto();
        responseDto.setUserName(user.getNickname());
        responseDto.setUserProfile(user.getProfile());
        responseDto.setCode(space.getCode());

        return responseDto;
    }
}
