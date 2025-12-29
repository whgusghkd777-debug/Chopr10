package com.mysite.sbb.music;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/list")
    public String list() {
        return "music/list";
    }

    // [중요] API 경로 명시 및 권한 확인 필요
    @GetMapping("/listJson")
    @ResponseBody
    public List<Music> listJson() {
        return musicService.getList();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Music music = musicService.getMusic(id);
        
        String videoId = "dQw4w9WgXcQ"; 
        String url = music.getUrl();

        if (url != null && !url.isEmpty()) {
            if (url.contains("v=")) {
                videoId = url.split("v=")[1].split("&")[0];
            } else if (url.contains("youtu.be/")) {
                videoId = url.split("youtu.be/")[1].split("\\?")[0];
            } else if (url.contains("embed/")) {
                videoId = url.split("embed/")[1].split("\\?")[0];
            }
        }
        
        // 프론트 detail_fragment.html에서 th:src="${embedUrl}"을 사용하므로 변수명을 맞춤
        model.addAttribute("music", music);
        model.addAttribute("embedUrl", "https://www.youtube.com/embed/" + videoId);
        
        return "music/detail_fragment"; 
    }
}