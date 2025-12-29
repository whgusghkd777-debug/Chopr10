package com.mysite.sbb.music;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;

    @GetMapping("/music/list")
    public String list() {
        return "music/list";
    }

    // [중요] 상세 페이지는 @ResponseBody를 절대 쓰지 않음
    @GetMapping("/music/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Music music = musicService.getMusic(id);
        model.addAttribute("music", music);
        model.addAttribute("embedUrl", convertToEmbedUrl(music.getUrl()));
        return "music/detail_fragment"; 
    }

    @GetMapping("/music/listJson")
    @ResponseBody
    public List<Music> listJson() {
        return musicService.getList();
    }

    private String convertToEmbedUrl(String url) {
        if (url == null || url.isEmpty()) return "";
        String videoId = "";
        String regex = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(regex);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) videoId = matcher.group();
        return "https://www.youtube.com/embed/" + videoId;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/like/{id}")
    @ResponseBody
    public Map<String, Object> like(@PathVariable("id") Integer id, Principal principal) {
        Music music = musicService.getMusic(id);
        SiteUser user = userService.getUser(principal.getName());
        musicService.like(music, user);
        Map<String, Object> res = new HashMap<>();
        res.put("likes", music.getLikers().size());
        return res;
    }
}