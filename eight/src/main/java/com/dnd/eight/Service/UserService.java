package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.UserRequestDto;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Boolean save(UserRequestDto userDto){
        if(userDto.getEmail() == null)
            return false;

        User user = userRepository.findByEmail(userDto.getEmail())
                .map(entity -> entity.update(userDto.getNickname(), userDto.getProfile()))
                .orElse(userDto.toEntity());

        userRepository.save(user);
        return true;
    }
}
