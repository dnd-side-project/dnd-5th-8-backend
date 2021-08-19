package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.UserRequestDto;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long save(UserRequestDto userDto){
        User user = userRepository.findByEmail(userDto.getEmail())
                .map(entity -> entity.update(userDto.getNickname(), userDto.getProfile()))
                .orElse(userDto.toEntity());

        return userRepository.save(user).getId();
    }

    @Transactional
    public User findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Email에 해당하는 user가 없습니다 " + id));
        return user;
    }

    @Transactional
    public String update(Long id, String profile, String nickname){
        User user = findById(id);
        user.update(nickname, profile);
        return user.getProfile();
    }

    @Transactional
    public String findProfile(Long id) {
        User user = findById(id);
        return user.getProfile();
    }
}
