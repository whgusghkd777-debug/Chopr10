package com.mysite.sbb.music.controller;

import com.mysite.sbb.music.Music;
import com.mysite.sbb.music.MusicService;
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

@RequestMapping("/music")
@RequiredArgsConstructor
@Controller
public class MusicController {

    // 반드시 이 이름(musicService)과 아래에서 쓰는 이름이 같아야 합니다!
    private final MusicService musicService; 
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        List<MusicListDto> musicList = this.musicService.getList(); 
        model.addAttribute("musicList", musicList);
        return "music_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String musicCreate() {
        return "music_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String musicCreate(@RequestParam String title, 
                              @RequestParam String artist, 
                              @RequestParam String url, 
                              @RequestParam String content,
                              Principal principal) {
        SiteUser author = this.userService.getUser(principal.getName());
        this.musicService.create(title, artist, url, content, author);
        return "redirect:/music/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Music music = this.musicService.getMusic(id);
        model.addAttribute("music", music);
        
        if (music.getUrl() != null && music.getUrl().contains("v=")) {
            String videoId = music.getUrl().split("v=")[1].split("&")[0];
            model.addAttribute("embedUrl", "https://www.youtube.com/embed/" + videoId);
        }
        return "music_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String musicVote(Principal principal, @PathVariable("id") Integer id) {
        Music music = this.musicService.getMusic(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.musicService.vote(music, siteUser);
        return String.format("redirect:/music/detail/%d", id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String musicDelete(@PathVariable("id") Integer id) {
        Music music = this.musicService.getMusic(id);
        this.musicService.delete(music);
        return "redirect:/music/list";
    }
}