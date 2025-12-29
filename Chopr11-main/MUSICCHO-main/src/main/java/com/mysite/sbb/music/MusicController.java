package com.mysite.sbb.music;

import com.mysite.sbb.music.dto.MusicListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/list")
    public String list(Model model) {
        List<MusicListDto> musicList = musicService.getList();
        model.addAttribute("musicList", musicList);
        return "music/list"; // templates/music/list.html 반환
    }
}