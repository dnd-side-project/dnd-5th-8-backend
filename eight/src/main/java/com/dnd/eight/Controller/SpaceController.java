package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.CheckSpaceCode;
import com.dnd.eight.Controller.Dto.SpaceAttendDto;
import com.dnd.eight.Service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping("/space/create")
    public String createSpace() {
        return spaceService.createSpace();
    }

    @GetMapping("/space/attend/{code}")
    public CheckSpaceCode attendSpace(@PathVariable String code) {
        List<SpaceAttendDto> codeList = spaceService.attendSpace(code);
        String result = "";
        Boolean check = false;

        if(codeList.size() != 0) result = codeList.get(0).getCode();
        if(result.equals(code)) check = true;

        CheckSpaceCode ans = new CheckSpaceCode();
        ans.setCheck(check);

        return ans;
    }
}
