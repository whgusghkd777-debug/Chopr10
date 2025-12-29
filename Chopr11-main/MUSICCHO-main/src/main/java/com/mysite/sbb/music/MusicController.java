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
        try {
            model.addAttribute("musicList", musicService.getList());
            return "music/list";
        } catch (Exception e) {
            model.addAttribute("error", "데이터 로드 실패: " + e.getMessage());
            return "error";  // templates/error.html 만들어 간단히 표시
        }
    }
}