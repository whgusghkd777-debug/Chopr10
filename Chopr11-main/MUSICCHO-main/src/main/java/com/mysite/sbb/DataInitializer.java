package com.mysite.sbb;

import com.mysite.sbb.music.MusicService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final MusicService musicService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // 1. 관리자 유저 생성
        SiteUser admin;
        try {
            admin = userService.getUser("admin");
        } catch (Exception e) {
            admin = userService.create("admin", "admin@test.com", "1234");
        }

        // 2. 초기 데이터 생성
        // MusicService.create()의 파라미터 순서: 제목, 가수, URL, 내용, 카테고리, 작성자
        musicService.create(
            "Sample Song", 
            "Artist", 
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ", 
            "This is a sample music description.", 
            "K-POP", 
            admin
        );
    }
}