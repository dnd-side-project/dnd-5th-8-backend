package com.dnd.eight.Service;

import com.dnd.eight.Controller.Dto.FamilyResponseDto;
import com.dnd.eight.Controller.Dto.SpaceIdUpdateDto;
import com.dnd.eight.Controller.Dto.SpaceRequestDto;
import com.dnd.eight.Domain.DailyQuestion.Question;
import com.dnd.eight.Domain.DailyQuestion.QuestionRepository;
import com.dnd.eight.Domain.DailyQuestion.SpaceQuestion;
import com.dnd.eight.Domain.DailyQuestion.SpaceQuestionRepository;
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

@RequiredArgsConstructor
@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final SpaceQuestionRepository spaceQuestionRepository;

    @Transactional
    public Long updateSpaceId(Long user_id, SpaceIdUpdateDto spaceIdUpdateDto) {
        User user = userRepository.findById(user_id)
                .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+user_id));

        Space space = spaceRepository.findByCode(spaceIdUpdateDto.getCode()).orElse(null);

        space.addUser(user); // 이 부분에서 add가 안되는 듯?

        return space.getId();
    }

    @Transactional
    public String createSpace(SpaceRequestDto spaceRequestDto) {

        int length = 5;
        String randomCode = getRandomStr(length);

        Long user_id = Long.valueOf(spaceRequestDto.getId());
        User user = userRepository.findById(user_id)
                .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+user_id));

        Space space = spaceRepository.save(Space.builder()
                .code(randomCode)
                .name(spaceRequestDto.getName())
                .question_number(1L)
                .count(1)
                .familyTalk(false)
                .roommasterId(user_id)
                .build()
        );

        space.addUser(user);

        Question question = questionRepository.findById(1L).orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+1L));

        SpaceQuestion spaceQuestion = SpaceQuestion.createSpaceQuestion(question, space);
        spaceQuestionRepository.save(spaceQuestion);
        return randomCode;
    }

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

    @Transactional(readOnly = true)
    public LinkedHashMap<String, Object> attend(String code) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Space space = spaceRepository.findByCode(code).orElse(null);

        if(space == null){ // 코드에 해당하는 space x
            map.put("spaceName", "");
            map.put("userCount", 0L);
            map.put("isExist", false);
            map.put("roomMaster", "");
            map.put("family", null);
        }
        else{
            // 기존 응답
            User master = userRepository.findById(space.getRoommasterId())
                    .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+space.getRoommasterId()));
            map.put("spaceName", space.getName());
            map.put("userCount", space.getUsers().stream().count());
            map.put("isExist", true);
            map.put("roomMaster", master.getNickname());

            // 스페이스에 참가중인 가족 구성원 리스트
            List<Object> familyList = new ArrayList<>();
            List<User> users = space.getUsers();
            FamilyResponseDto familyResponseDto;

            for(User user: users) {
                familyResponseDto = new FamilyResponseDto();
                familyResponseDto.setUserId(user.getId());
                familyResponseDto.setNickname(user.getNickname());
                familyResponseDto.setProfile(user.getProfile());
                familyList.add(familyResponseDto);
            }

            map.put("family", familyList);
        }

        return map;
    }

    @Transactional
    public Boolean getFamilyTalk(Long spaceId){
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(()->new IllegalArgumentException("해당 ID가 존재하지 않습니다. id="+spaceId));

        if(!space.getFamilyTalk()){
            space.updateFamilyTalk();
            return false;
        }

        return space.getFamilyTalk();
    }
}
