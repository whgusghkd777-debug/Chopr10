package com.mysite.sbb.music.controller;

import com.mysite.sbb.music.Music;
import com.mysite.sbb.music.MusicService;
import com.mysite.sbb.music.dto.MusicListDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/music")
@Controller
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<MusicListDto> musicList = this.musicService.getList();
        model.addAttribute("musicList", musicList); 
        return "music_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) { // Long 대신 Integer 사용
        Music music = this.musicService.getMusic(id);
        model.addAttribute("music", music);
        
        if (music.getUrl() != null && music.getUrl().contains("watch?v=")) {
            String embedUrl = music.getUrl().replace("watch?v=", "embed/");
            model.addAttribute("embedUrl", embedUrl);
        }
        return "music_detail";
    }
}