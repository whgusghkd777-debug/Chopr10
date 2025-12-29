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

    @GetMapping("/listJson")
    @ResponseBody
    public List<Music> listJson() {
        return musicService.getList();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Music music = musicService.getMusic(id);
        
        // [실무 핵심] 안정적인 유튜브 ID 추출 로직
        String videoId = "dQw4w9WgXcQ"; // 기본값 (에러 방지)
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
        
        // 템플릿에서 바로 사용할 수 있도록 임베드용 주소로 교체
        music.setUrl("https://www.youtube.com/embed/" + videoId);
        
        model.addAttribute("music", music);
        return "music/detail_fragment"; 
    }
}