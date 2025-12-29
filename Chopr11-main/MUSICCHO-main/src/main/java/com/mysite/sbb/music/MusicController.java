package com.mysite.sbb.music;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("musicList", musicService.getList());
        return "music/list";  // ← 여기 꼭 "music/list" (templates/music/list.html 찾음)
    }
}