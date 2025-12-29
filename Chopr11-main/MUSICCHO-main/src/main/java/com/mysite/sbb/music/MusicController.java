package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    // 메인 페이지 (layout 사용)
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("content", "music/list :: content");
        return "layout";
    }

    // JS에서 데이터 불러올 API
    @GetMapping("/list-data")
    @ResponseBody
    public List<Music> listData() {
        return musicService.getListWithDetails();  // MusicService에 추가 필요 (answerList, likes 포함)
    }

    // 상세 데이터 API (모달용)
    @GetMapping("/detail/{id}")
    @ResponseBody
    public Music detail(@PathVariable Integer id, Principal principal) {
        Music music = musicService.getMusic(id);
        if (principal != null) {
            SiteUser user = userService.getUser(principal.getName());
            music.setIsLiked(musicService.isLiked(music, user));  // 좋아요 상태 추가
        }
        return music;
    }

    // 음악 공유 (파일 or 유튜브)
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@Valid MusicCreateForm form,
                         @RequestParam(value = "audioFile", required = false) MultipartFile audioFile,
                         Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        String finalUrl = form.getUrl();

        if (audioFile != null && !audioFile.isEmpty()) {
            String uploadDir = "C:/sbb_uploads/music/";
            new File(uploadDir).mkdirs();
            String fileName = System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
            try {
                audioFile.transferTo(new File(uploadDir + fileName));
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패");
            }
            finalUrl = "/uploads/" + fileName;
        }

        musicService.create(form.getTitle(), form.getArtist(), finalUrl, form.getContent(), form.getCategory(), user);
        return "redirect:/music/list";
    }

    // 좋아요 토글
    @PostMapping("/like/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> like(@PathVariable Integer id, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        boolean isLiked = musicService.toggleLike(id, user);
        int likes = musicService.getLikesCount(id);

        Map<String, Object> result = new HashMap<>();
        result.put("likes", likes);
        result.put("isLiked", isLiked);
        return ResponseEntity.ok(result);
    }

    // 관리자 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        musicService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}