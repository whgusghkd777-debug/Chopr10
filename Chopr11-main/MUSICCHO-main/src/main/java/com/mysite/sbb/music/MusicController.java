package com.mysite.sbb.music;

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

    @GetMapping("/music/listJson")
    @ResponseBody // [중요] 이 어노테이션이 있어야 JSON 데이터를 보냅니다.
    public List<Music> listJson() {
        return musicService.getList();
    }

    @GetMapping("/music/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        try {
            Music music = musicService.getMusic(id);
            model.addAttribute("music", music);
            return "music/detail_fragment"; 
        } catch (Exception e) {
            // [실무 팁] 데이터가 없으면 리스트로 리다이렉트 시켜 500 에러 방지
            return "redirect:/music/list";
        }
    }
}