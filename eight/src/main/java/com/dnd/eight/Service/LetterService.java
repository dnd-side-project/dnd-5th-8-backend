package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.*;
import com.dnd.eight.Domain.Letter.Letter;
import com.dnd.eight.Domain.Letter.LetterRepository;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Domain.Login.UserRepository;
import com.dnd.eight.Domain.Space.Space;
import com.dnd.eight.Domain.Space.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public LinkedHashMap<String, Object> recieveLetterList(RecieveLetterRequestDto recieveLetterRequestDto) {
        long spaceId = recieveLetterRequestDto.getSpaceId();
        long userId = recieveLetterRequestDto.getUserId();

        RecieveLetterResponseDto recieveLetterResponseDto;
        FamilyResponseDto familyResponseDto;

        List<Object> familyList = new ArrayList<>();
        List<Object> recieveList = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + spaceId));
        List<User> users = space.getUsers();

        for(User user: users) {
            if(user.getId() != userId) { // 본인 빼고
                familyResponseDto = new FamilyResponseDto();
                familyResponseDto.setUserId(user.getId());
                familyResponseDto.setNickname(user.getNickname());
                familyResponseDto.setProfile(user.getProfile());
                familyList.add(familyResponseDto);
            }
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + userId));
        List<Letter> letters = user.getToletters();

        for(Letter letter: letters) {
            recieveLetterResponseDto = new RecieveLetterResponseDto();
            recieveLetterResponseDto.setUserId(letter.getFrom_user().getId());
            recieveLetterResponseDto.setRecieveNickname(letter.getFrom_user().getNickname());
            recieveLetterResponseDto.setRecieveNickname(letter.getFrom_user().getProfile());
            recieveLetterResponseDto.setContent(letter.getContent());
            recieveList.add(recieveLetterResponseDto);
        }

        map.put("family", familyList);
        map.put("recieveletter", recieveList);

        return map;
    }

    @Transactional(readOnly = true)
    public List<SendLetterResponseDto> sendLetterList(Long userId) {
        List<SendLetterResponseDto> responseDtoList = new ArrayList<>();
        SendLetterResponseDto sendLetterResponseDto;

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + userId));
        List<Letter> letters = user.getFromletters();

        for(Letter letter: letters) {
            sendLetterResponseDto = new SendLetterResponseDto();
            sendLetterResponseDto.setToNickname(letter.getTo_user().getNickname());
            sendLetterResponseDto.setToNickname(letter.getTo_user().getProfile());
            sendLetterResponseDto.setContent(letter.getContent());
            responseDtoList.add(sendLetterResponseDto);
        }

        return responseDtoList;
    }

    @Transactional
    public Long saveLetter(SendLetterRequestDto sendLetterRequestDto) {
        Long to_userId = sendLetterRequestDto.getTouserId();
        Long from_userId = sendLetterRequestDto.getFromuserId();
        String content = sendLetterRequestDto.getContent();
        User to_user = userRepository.findById(to_userId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + to_userId));
        User from_user = userRepository.findById(from_userId).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id=" + from_userId));
        Letter letter = Letter.saveLetter(content, to_user, from_user);

        return letterRepository.save(letter).getId();
    }
}
