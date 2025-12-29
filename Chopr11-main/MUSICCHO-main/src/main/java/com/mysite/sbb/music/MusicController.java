package com.mysite.sbb.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("musicList", musicService.getList());
        return "music/list";
    }
}