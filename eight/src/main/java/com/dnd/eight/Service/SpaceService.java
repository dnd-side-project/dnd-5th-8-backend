package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.SpaceAttendDto;
import com.dnd.eight.Controller.Dto.SpaceRequestDto;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import com.dnd.eight.Domain.Space.Space;
import com.dnd.eight.Domain.Space.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createSpace(SpaceRequestDto spaceRequestDto) {

        int length = 10;
        String randomCode = getRandomStr(length);

        Space space = spaceRepository.save(Space.builder()
                .code(randomCode)
                .name(spaceRequestDto.getName())
                .question_number(1)
                .count(1)
                .build()
        );

        Long user_id = Long.valueOf(spaceRequestDto.getId());
        User user = userRepository.findById(user_id)
                .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+user_id));
        space.addUser(user);


        return randomCode;
    }
/*
    @Transactional
    public String createSpace(HashMap<String, String> map) {

        int length = 10;
        String randomCode = getRandomStr(length);

        Space space = spaceRepository.save(Space.builder()
                .code(randomCode)
                .name(map.get("name"))
                .question_number(1)
                .count(1)
                .build()
        );
        Long user_id = Long.valueOf(map.get("user_id"));
        User user = userRepository.findById(user_id).orElseThrow();
        space.addUser(user);
        return randomCode;
        //return spaceRepository.save(spaceResponseDto.toEntity()).getCode();
    }*/

    public String getRandomStr(int size) {
        char[] tmp = new char[size];
        for (int i = 0; i < tmp.length; i++) {
            int div = (int) Math.floor(Math.random() * 2);

            if (div == 0) { // 0이면 숫자로
                tmp[i] = (char) (Math.random() * 10 + '0');
            } else { //1이면 알파벳
                tmp[i] = (char) (Math.random() * 26 + 'A');
            }
        }
        return new String(tmp);
    }

    public List<SpaceAttendDto> attendSpace(String code) {
        return spaceRepository.findByCode(code).stream()
                .map(SpaceAttendDto::new)
                .collect(Collectors.toList());
    }
}
