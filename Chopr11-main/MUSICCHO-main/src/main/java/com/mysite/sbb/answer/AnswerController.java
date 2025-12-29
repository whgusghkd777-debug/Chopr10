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

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final MusicService musicService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable("id") Integer id, @RequestParam("content") String content, Principal principal) { // Long -> Integer
        Music music = musicService.getMusic(id);
        SiteUser user = userService.getUser(principal.getName());
        answerService.create(music, content, user);
        // 답변 작성 후 방금 본 음악 상세 페이지로 돌아가는 것이 실무적입니다.
        return String.format("redirect:/music/detail/%s", id);
    }
}