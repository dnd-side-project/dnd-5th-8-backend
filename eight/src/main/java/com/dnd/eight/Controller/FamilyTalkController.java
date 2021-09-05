package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.CategoryRequestDto;
import com.dnd.eight.Controller.Dto.FamilyTalkQuestionRequestDto;
import com.dnd.eight.Controller.Message.*;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FamilyTalkController {
    private final SpaceService spaceService;
    private final UserService userService;
    private final FamilyTalkService familyTalkService;

    private final SimpMessagingTemplate template;
    private final HashMap<Long, Integer> roundMap = new HashMap<>();
    private final HashMap<Long, List<FamilyTalkCategoryMessage>> categoryMap = new HashMap<>();
    private final HashMap<Long, List<Long>> userMap = new HashMap<>();
    private List<FamilyTalkCategoryMessage> categoryList;
    private List<Long> userList;

    //====================== REST API =======================
    //카테고리 등록
    @PostMapping("/family-talk/questions/category")
    public Long createCategory(@RequestBody CategoryRequestDto requestDto) throws InterruptedException {
        Thread.sleep(1000);
        return familyTalkService.createSubCategory(requestDto.getParentCategoryId(), requestDto.getContent());
    }

    //질문 등록
    @PostMapping("/family-talk/questions")
    public Long createQuestions(@RequestBody FamilyTalkQuestionRequestDto requestDto){
        return familyTalkService.createQuestions(requestDto.getSubCategoryId(), requestDto.getContent());
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

    //3. family-talk 질문 요창
    @GetMapping("/family-talk/questions/{round}")
    public List<String> getQuestions(@PathVariable int round){
        List<Long> categoryIdList = new ArrayList<>();
        categoryIdList.add(2L);
        categoryIdList.add(3L);
        categoryIdList.add(9L);
        return familyTalkService.getQuestions(categoryIdList, round);
    }

    //====================== Web Socket =======================

    // 0. 게임 active 여부
    @MessageMapping("/active")
    public void isFamilyTalkActive(ActiveRequestMessage message) throws InterruptedException {
        FamilyTalkActiveMessage responseMessage = new FamilyTalkActiveMessage();
        Thread.sleep(2000);

        if(roundMap.containsKey(message.getSpaceId())){
            responseMessage.setActive(true);
        }
        else{
            responseMessage.setActive(false);
        }
        template.convertAndSend("/queue/active/"+message.getSpaceId(), responseMessage);
    }

    // 1. 게임 생성자가 round 수를 누르고 나서
    @MessageMapping("/round")
    public void receiveRound(FamilyTalkRoundMessage message){
        FamilyTalkReadyMessage readyMessage = new FamilyTalkReadyMessage();
        userList = new ArrayList<>();
        userList.add(message.getUserId());
        userMap.put(message.getSpaceId(), userList);

        User user = userService.findById(message.getUserId());
        readyMessage.setNickName(user.getNickname());
        readyMessage.setProfile(user.getProfile());
        roundMap.put(message.getSpaceId(), message.getRound());

        FamilyTalkActiveMessage activeMessage = new FamilyTalkActiveMessage();
        activeMessage.setActive(true);
        System.out.println("/queue/active/"+message.getSpaceId());

        template.convertAndSend("/topic/ready/"+message.getSpaceId(), readyMessage);
        template.convertAndSend("/queue/active/"+message.getSpaceId(), activeMessage);
    }

    // 2. 게임 참가자가 참여하기를 눌렀을 때
    @MessageMapping("/join")
    public void joinGame(FamilyTalkJoinMessage message){
        FamilyTalkReadyMessage readyMessage = new FamilyTalkReadyMessage();
        userMap.get(message.getSpaceId()).add(message.getUserId());

        User user = userService.findById(message.getUserId());
        readyMessage.setNickName(user.getNickname());
        readyMessage.setProfile(user.getProfile());

        template.convertAndSend("/topic/ready/"+message.getSpaceId(), readyMessage);
    }

    // 3. 게임 생성자가 '다 모였어요' 버튼 눌렀을 때
    @MessageMapping("/assemble")
    public void assemble(AssembleRequestMessage message){
        AssembleResponseMessage responseMessage = new AssembleResponseMessage();
        responseMessage.setAssemble(true);
        template.convertAndSend("/topic/assemble/"+message.getSpaceId(), responseMessage);
    }

    // 4. 카테고리 클릭 시
    @MessageMapping("/category")
    public void chooseCategory(FamilyTalkCategoryMessage message){
        FamilyTalkCategoryMessage categoryMessage = new FamilyTalkCategoryMessage();
        if(!categoryMap.containsKey(message.getSpaceId())){
            categoryList = new ArrayList<>();
            categoryMessage.setCategoryId(message.getCategoryId());
            categoryMessage.setColor(message.getColor());
            categoryList.add(categoryMessage);
            categoryMap.put(message.getSpaceId(), categoryList);
        }
        else{
            categoryMessage.setCategoryId(message.getCategoryId());
            categoryMessage.setColor(message.getColor());
            categoryMap.get(message.getSpaceId()).add(categoryMessage);
        }
        template.convertAndSend("/topic/category/"+message.getSpaceId(), categoryMap.get(message.getSpaceId()));
    }

    // 5. 게임 시작
    @MessageMapping("/start") //request: spaceId, response(topic): round, questions
    public void startGame(StartRequestMessage message){
        StartResponseMessage responseMessage = new StartResponseMessage();
        List<Long> categoryIdList = new ArrayList<>();
        List<FamilyTalkCategoryMessage> categoryMsgs = categoryMap.get(message.getSpaceId());
        for(FamilyTalkCategoryMessage msg : categoryMsgs){
            categoryIdList.add(msg.getCategoryId());
        }

        Integer roundNumber = roundMap.get(message.getSpaceId());
        List<String> questions = familyTalkService.getQuestions(categoryIdList, roundNumber);
        responseMessage.setQuestions(questions);
        responseMessage.setRoundNumber(roundNumber);

        template.convertAndSend("/topic/game/start/"+message.getSpaceId(), responseMessage);
    }

    // 6. 순서배정
    @MessageMapping("/order")
    public void orderList(StartRequestMessage message){
        List<Long> users = userMap.get(message.getSpaceId());
        Collections.shuffle(users);
        template.convertAndSend("/topic/game/order/"+message.getSpaceId(), users);
    }

    // 7. 시간 추가
    @MessageMapping("/add-time")
    public void addTime(AddTimeMessage message){
        template.convertAndSend("/topic/game/add-time/"+message.getSpaceId(), message);
    }
}
