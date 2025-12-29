package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    private static final String UPLOAD_DIR = "C:/sbb_uploads/music/";  // WebConfig와 동일

    @GetMapping("/music/list")
    public String list(Model model) {
        model.addAttribute("musicList", musicService.getList());
        return "music/list";
    }

    @GetMapping("/music/listJson")
    @ResponseBody
    public List<Music> listJson() {
        return musicService.getList();
    }

    @GetMapping("/music/detail/{id}")
    @ResponseBody
    public Music detail(@PathVariable Integer id) {
        return musicService.getMusic(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/create")
    @ResponseBody
    public Music create(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "audioFile", required = false) MultipartFile audioFile,
            @RequestParam(value = "category", defaultValue = "기타") String category,
            Principal principal) {

        SiteUser user = userService.getUser(principal.getName());

        if (title == null || title.trim().isEmpty() || artist == null || artist.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 아티스트는 필수입니다.");
        }

        String finalUrl = url;

        if (audioFile != null && !audioFile.isEmpty()) {
            if (url != null && !url.trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL과 파일은 동시에 올릴 수 없습니다.");
            }

            try {
                String originalFilename = audioFile.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String savedFilename = UUID.randomUUID().toString() + extension;

                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) dir.mkdirs();

                File savedFile = new File(UPLOAD_DIR + savedFilename);
                audioFile.transferTo(savedFile);

                finalUrl = "/uploads/music/" + savedFilename;  // WebConfig에서 /uploads/** 서빙
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장 실패");
            }
        } else if (url == null || url.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "YouTube URL 또는 오디오 파일 하나는 필요합니다.");
        }

        if (description == null) description = "";

        return musicService.create(title, artist, description, finalUrl, category, user);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/like/{id}")
    @ResponseBody
    public Map<String, Object> like(@PathVariable Integer id, Principal principal) {
        Music music = musicService.getMusic(id);
        SiteUser user = userService.getUser(principal.getName());
        musicService.like(music, user);

        Map<String, Object> response = new HashMap<>();
        response.put("likes", music.getLikes());
        response.put("isLiked", music.getLikers().contains(user));
        return response;
    }
}