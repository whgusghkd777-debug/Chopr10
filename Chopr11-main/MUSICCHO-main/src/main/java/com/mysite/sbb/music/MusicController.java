package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/music/list")
    public String list() {
        return "music/list";
    }

    // [중요] @ResponseBody가 있으면 안 됨! 화면(HTML)을 보여줘야 함
    @GetMapping("/music/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Music music = musicService.getMusic(id);
        model.addAttribute("music", music);
        
        // 유튜브 embed 주소 변환 로직
        String videoId = "";
        if(music.getUrl() != null && music.getUrl().contains("v=")) {
            videoId = music.getUrl().split("v=")[1].split("&")[0];
        }
        model.addAttribute("embedUrl", "https://www.youtube.com/embed/" + videoId);
        
        return "music/detail_fragment"; // templates/music/detail_fragment.html 호출
    }
}