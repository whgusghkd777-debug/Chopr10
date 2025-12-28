package com.mysite.sbb.music;

import com.mysite.sbb.music.dto.MusicListDto;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    // 루트 매핑 삭제! (MainController와 중복 방지)

    @GetMapping({"/music", "/music/"})
    public String index(Model model) {
        List<MusicListDto> musicList = musicService.getList();
        List<MusicListDto> rankingList = musicService.getRankingListDto();

        model.addAttribute("musicList", musicList);
        model.addAttribute("rankingList", rankingList);

        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/music/create")
    public String createForm() {
        return "music_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/upload")
    public String upload(@RequestParam("title") String title,
                         @RequestParam("artist") String artist,
                         @RequestParam("url") String url,
                         Principal principal) {

        SiteUser siteUser = userService.getUser(principal.getName());
        musicService.create(title, artist, url, "", "", siteUser);

        return "redirect:/music/";
    }

    @GetMapping("/music/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Music music = musicService.getMusic(id);
        String embedUrl = musicService.getEmbedUrl(music.getUrl());

        model.addAttribute("music", music);
        model.addAttribute("embedUrl", embedUrl);

        return "music_detail";
    }
}