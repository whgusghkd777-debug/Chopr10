package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    @GetMapping("/music/list")
    public String list() {
        return "music/list";
    }

    // [중요] listJson이 있어야 404가 안 뜸
    @GetMapping("/music/listJson")
    @ResponseBody
    public List<Music> listJson() {
        return musicService.getList();
    }

    @GetMapping("/music/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Music music = musicService.getMusic(id);
        model.addAttribute("music", music);
        return "music/detail_fragment"; 
    }
}