package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.*;
import com.dnd.eight.Domain.FamilyTalk.ParentCategory;
import com.dnd.eight.Domain.Login.User;
import com.dnd.eight.Service.FamilyTalkService;
import com.dnd.eight.Service.SpaceService;
import com.dnd.eight.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 1. Disconnect 할 때 space의 familytalk false로 변환해야함
 * */

@RestController
@RequiredArgsConstructor
public class FamilyTalkController {
    private final SpaceService spaceService;
    private final UserService userService;
    private final FamilyTalkService familyTalkService;

    private final SimpMessagingTemplate template;
    private final HashMap<Long, Integer> round = new HashMap<>();
    private final HashMap<Long, List<FamilyTalkReadyMessage>> inGameUser = new HashMap<>();
    private List<FamilyTalkReadyMessage> userList;



    /**
     * REST API
     * */
    //질문 등록
    @PostMapping("/family-talk/question/category")
    public Long createCategory(@RequestBody CategoryRequestDto requestDto){
        return familyTalkService.createSubCategory(requestDto.getParentCategoryId(), requestDto.getContent());
    }
    //1. family-talk 시작 여부 확인
    @GetMapping("/family-talk/start/{spaceId}")
    public Boolean isStart(@PathVariable Long spaceId){
        return spaceService.getFamilyTalk(spaceId);
    }

    //2. family-talk 카테고리 요청
    @GetMapping("/family-talk/category")
    public List<ParentCategory> getCategory(){
        return familyTalkService.getCategories();
    }


    /**
     * Web Socket
     * */
    // 1. 게임 생성자가 round 수를 누르고 나서
    @MessageMapping("/round")
    public void receiveRound(FamilyTalkRoundMessage message){
        userList = new ArrayList<>();
        FamilyTalkReadyMessage readyMessage = new FamilyTalkReadyMessage();

        User user = userService.findById(message.getUserId());
        readyMessage.setNickName(user.getNickname());
        readyMessage.setProfile(user.getProfile());
        userList.add(readyMessage);
        inGameUser.put(message.getSpaceId(), userList);
        round.put(message.getSpaceId(), message.getRound());

        FamilyTalkActiveMessage activeMessage = new FamilyTalkActiveMessage();
        activeMessage.setActive("active");

        template.convertAndSend("/topic/ready/"+message.getSpaceId(), inGameUser.get(message.getSpaceId()));
        template.convertAndSend("/queue/active/"+message.getSpaceId(), activeMessage);
    }

    // 2. 게임 참가자가 참여하기를 눌렀을 때
    @MessageMapping("/join")
    public void joinGame(FamilyTalkJoinMessage message){
        FamilyTalkReadyMessage readyMessage = new FamilyTalkReadyMessage();

        User user = userService.findById(message.getUserId());
        readyMessage.setNickName(user.getNickname());
        readyMessage.setProfile(user.getProfile());

        inGameUser.get(message.getSpaceId()).add(readyMessage);
        template.convertAndSend("/topic/ready/"+message.getSpaceId(), inGameUser.get(message.getSpaceId()));
    }

    // 3.
    @MessageMapping("/category")
    public void chooseCategory(){

    }



}
