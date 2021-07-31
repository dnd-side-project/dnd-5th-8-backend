package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.SpaceResponseDto;
import com.dnd.eight.Domain.Space.Space;
import com.dnd.eight.Domain.Space.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;

    @Transactional
    public String createSpace() {
        int length = 10;
        String randomCode = getRandomStr(length);

        spaceRepository.save(Space.builder()
                .code(randomCode)
                .question_number(1)
                .count(1)
                .build()
        );

        return randomCode;
    }

    public static String getRandomStr(int size) {
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

}
