package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ... (기존 import 유지)

@Controller
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("content", "music/list :: content");
        return "layout";
    }

    @GetMapping("/list-data")
    @ResponseBody
    public List<Music> listData() {
        return musicService.getList();
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public Music detail(@PathVariable Integer id, Principal principal) {
        return musicService.getMusic(id);  // isLiked 임시 생략
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@RequestParam String title,
                         @RequestParam String artist,
                         @RequestParam(required = false) String url,
                         @RequestParam(required = false) String content,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) MultipartFile audioFile,
                         Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        String finalUrl = url != null ? url : "";

        if (audioFile != null && !audioFile.isEmpty()) {
            String uploadDir = "C:/sbb_uploads/music/";
            new File(uploadDir).mkdirs();
            String fileName = System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
            try {
                audioFile.transferTo(new File(uploadDir + fileName));
                finalUrl = "/uploads/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException("ファイルアップロード失敗");
            }
        }

        musicService.create(title, artist, finalUrl, content, category, user);
        return "redirect:/music/list";
    }

    @PostMapping("/like/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> like(@PathVariable Integer id, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        musicService.toggleLike(id, user);  // +1만 함 (토글 원하면 likers Set 추가)
        int likes = musicService.getLikesCount(id);

        Map<String, Object> result = new HashMap<>();
        result.put("likes", likes);
        result.put("isLiked", true);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Integer id) {
        musicService.delete(id);
        return "redirect:/music/list";
    }
}