package com.mysite.sbb.music;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("musicList", musicService.getList());
        return "music/list";
    }
}