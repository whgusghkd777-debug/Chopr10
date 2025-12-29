package com.mysite.sbb.answer;

import com.mysite.sbb.music.Music;
import com.mysite.sbb.music.MusicService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final MusicService musicService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    @ResponseBody
    public Map<String, String> create(@PathVariable("id") Integer id, @RequestParam("content") String content, Principal principal) {
        Music music = this.musicService.getMusic(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.create(music, content, siteUser);
        
        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
}