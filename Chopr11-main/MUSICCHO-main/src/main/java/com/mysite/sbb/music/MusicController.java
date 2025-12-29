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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Controller
public class MusicController {

    private final MusicService musicService;
    private final UserService userService;
    private static final String UPLOAD_DIR = "C:/sbb_uploads/music/";

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

    // [修正] @ResponseBodyを削除し、詳細画面(HTML)を返すように変更
    @GetMapping("/music/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Music music = musicService.getMusic(id);
        model.addAttribute("music", music);
        
        // YouTubeのURLを埋め込み用(Embed)に変換
        String embedUrl = convertToEmbedUrl(music.getUrl());
        model.addAttribute("embedUrl", embedUrl);
        
        return "music/detail_fragment"; 
    }

    private String convertToEmbedUrl(String url) {
        if (url == null) return "";
        String videoId = "";
        String regex = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(regex);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group();
        }
        return "https://www.youtube.com/embed/" + videoId;
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
            @RequestParam(value = "category", defaultValue = "その他") String category,
            Principal principal) {

        SiteUser user = userService.getUser(principal.getName());
        if (title == null || title.trim().isEmpty() || artist == null || artist.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "タイトルとアーティストは必須です。");
        }

        String finalUrl = url;
        if (audioFile != null && !audioFile.isEmpty()) {
            try {
                String originalFilename = audioFile.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String savedFilename = UUID.randomUUID().toString() + extension;
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) dir.mkdirs();
                audioFile.transferTo(new File(UPLOAD_DIR + savedFilename));
                finalUrl = "/uploads/music/" + savedFilename;
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ファイルの保存に失敗しました。");
            }
        }
        return musicService.create(title, artist, description != null ? description : "", finalUrl, category, user);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/like/{id}")
    @ResponseBody
    public Map<String, Object> like(@PathVariable("id") Integer id, Principal principal) {
        Music music = musicService.getMusic(id);
        SiteUser user = userService.getUser(principal.getName());
        musicService.like(music, user);

        Map<String, Object> response = new HashMap<>();
        response.put("likes", music.getLikes());
        response.put("isLiked", music.getLikers().contains(user));
        return response;
    }
}