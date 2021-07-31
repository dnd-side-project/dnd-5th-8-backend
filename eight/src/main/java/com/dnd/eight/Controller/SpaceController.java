package com.dnd.eight.Controller;

import com.dnd.eight.Service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping("/space/create")
    public String createSpace() {
        return spaceService.createSpace();
    }
}
