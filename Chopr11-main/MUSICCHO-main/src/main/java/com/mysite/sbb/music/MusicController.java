package com.mysite.sbb.music;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mysite.sbb.user.SiteUser;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {  

    private final MusicService musicService;

    // 목록 (layout 없으면 "music/list" 직접 반환)
    @GetMapping("/list")
    public String list(Model model) {
        List<Music> musicList = musicService.getList();
        model.addAttribute("musicList", musicList);
        return "music/list";  // templates/music/list.html
    }

    // 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Music music = musicService.getMusic(id);
        model.addAttribute("music", music);
        return "music/detail";
    }

    // 생성 폼 (로그인 필요하면 @PreAuthorize 추가)
    @GetMapping("/create")
    public String createForm() {
        return "music/create";
    }

    // 생성 처리 (직접 파라미터 받음)
    @PostMapping("/create")
    public String create(@RequestParam String title,
                         @RequestParam String artist,
                         @RequestParam(required = false) String url,
                         @RequestParam(required = false) String content,
                         @RequestParam(required = false) String category,
                         Principal principal) {
        // principal null 체크 필요하면 UserService로 user 가져와
        SiteUser author = null;  // 임시 null (로그인 구현 후 수정)
        musicService.create(title, artist, url, content, category, author);
        return "redirect:/music/list";
    }

    // 삭제 (관리자만)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        musicService.delete(id);
        return "redirect:/music/list";
    }
}