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

@Controller
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    // 메인 페이지
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("content", "music/list :: content");  // layout.html 사용 시
        return "layout";  // 또는 "list" если layout 없음
    }

    // JS 데이터 API
    @GetMapping("/list-data")
    @ResponseBody
    public List<Music> listData() {
        return musicService.getList();  // 기본 getList() 사용 (answerList fetch join 필요하면 Service 수정)
    }

    // 상세 API
    @GetMapping("/detail/{id}")
    @ResponseBody
    public Music detail(@PathVariable Integer id, Principal principal) {
        Music music = musicService.getMusic(id);
        if (principal != null) {
            SiteUser user = userService.getUser(principal.getName());
            // isLiked는 Music에 transient boolean으로 추가하거나 Service에서 계산
            // 임시로 false
            // music.setIsLiked(...);
        }
        return music;
    }

    // 음악 공유 (MusicCreateForm 없애고 파라미터 직접)
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
        String finalUrl = url;

        if (audioFile != null && !audioFile.isEmpty()) {
            String uploadDir = "C:/sbb_uploads/music/";
            new File(uploadDir).mkdirs();
            String fileName = System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
            try {
                audioFile.transferTo(new File(uploadDir + fileName));
                finalUrl = "/uploads/" + fileName;  // properties 매핑 확인
            } catch (IOException e) {
                throw new RuntimeException("ファイルアップロード失敗");
            }
        }

        musicService.create(title, artist, finalUrl, content, category, user);
        return "redirect:/music/list";
    }

    // 좋아요 (Music에 int likes = 0; 추가 가정, toggle은 간단 +1/-1)
    @PostMapping("/like/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> like(@PathVariable Integer id, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        boolean isLiked = musicService.toggleLike(id, user);  // Service에 구현 필요
        int likes = musicService.getLikesCount(id);

        Map<String, Object> result = new HashMap<>();
        result.put("likes", likes);
        result.put("isLiked", isLiked);
        return ResponseEntity.ok(result);
    }

    // 삭제 (DELETE 대신 POST로 변경 추천, JS에서 POST 사용)
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Integer id) {
        musicService.delete(id);
        return "redirect:/music/list";
    }
}