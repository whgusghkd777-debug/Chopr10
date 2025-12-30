package com.mysite.sbb.music.controller;

import com.mysite.sbb.music.Music;
import com.mysite.sbb.music.MusicService;
import com.mysite.sbb.music.dto.MusicListDto;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequestMapping("/music")
@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService; 
    private final UserService userService;

    @Value("${file.upload-path}")
    private String uploadPath;

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
                              @RequestParam(required = false) String url, 
                              @RequestParam String content,
                              @RequestParam("file") MultipartFile file, // 파일 받기 추가
                              Principal principal) throws IOException {
        
        SiteUser author = this.userService.getUser(principal.getName());
        String fileName = null;

        // 파일 업로드 처리
        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // 파일명 중복 방지를 위해 UUID 사용
            fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File saveFile = new File(uploadPath, fileName);
            file.transferTo(saveFile);
        }

        // 서비스 호출 (수정된 create 메서드에 맞춰 fileName 전달)
        this.musicService.create(title, artist, url, content, author, fileName);
        
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