package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    // 기존 HTML 리스트 페이지
    @GetMapping("/music/list")
    public String list(Model model) {
        model.addAttribute("musicList", musicService.getList());
        return "music/list";
    }

    // 프론트 JS용 JSON 리스트 (랭킹 포함)
    @GetMapping("/music/listJson")
    @ResponseBody
    public List<Music> listJson() {
        return musicService.getList();
    }

    // 상세 정보 JSON
    @GetMapping("/music/detail/{id}")
    @ResponseBody
    public Music detail(@PathVariable Integer id) {
        return musicService.getMusic(id);
    }

    // 음악 공유 (POST) - 파라미터 맞춤 + category 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/create")
    @ResponseBody
    public Music create(@RequestBody Map<String, String> body, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());

        String title = body.get("title");
        String artist = body.get("artist");
        String description = body.get("description");  // 프론트에서 description으로 보냄
        String url = body.get("url");
        String category = body.get("category");        // 프론트에서 category 보내면 사용, 없으면 "기타"

        // 필수값 체크 (초보자라 안전하게)
        if (title == null || title.trim().isEmpty() ||
            artist == null || artist.trim().isEmpty() ||
            url == null || url.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목, 아티스트, URL은 필수입니다.");
        }

        // category 없으면 기본값 "기타"
        if (category == null || category.trim().isEmpty()) {
            category = "기타";
        }

        // Service 호출 (파라미터 6개 정확히 맞춤)
        return musicService.create(title, artist, description, url, category, user);
    }

    // 좋아요 토글 (기존 그대로, 문제 없음)
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