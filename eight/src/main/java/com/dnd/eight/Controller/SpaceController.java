package com.dnd.eight.Controller;

import com.dnd.eight.Controller.Dto.SpaceIdUpdateDto;
import com.dnd.eight.Controller.Dto.SpaceRequestDto;
import com.dnd.eight.Controller.Dto.SpaceResponseDto;
import com.dnd.eight.Service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SpaceController {
    private final SpaceService spaceService;

    @PostMapping("/space/create")
    public String createSpace(@RequestBody SpaceRequestDto spaceRequestDto) {

        return spaceService.createSpace(spaceRequestDto);
    }

    @GetMapping("/space/attend/{code}")
    public SpaceResponseDto attendSpace(@PathVariable String code) {

        return spaceService.attend(code);
    }


    @PutMapping("/space/attend/{id}")
    public Long updateUserSpaceId(@PathVariable Long id, @RequestBody SpaceIdUpdateDto spaceIdUpdateDto) {
        return spaceService.updateSpaceId(id, spaceIdUpdateDto);
    }
}
