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
        // 초기 유저 생성 (없을 경우)
        SiteUser admin;
        try {
            admin = userService.getUser("admin");
        } catch (Exception e) {
            admin = userService.create("admin", "admin@test.com", "1234");
        }

        // 초기 음악 데이터 생성 (목록이 비어있을 때만)
        if (musicService.getList().stream().noneMatch(m -> m.title().contains("サンプル"))) {
            musicService.create("サンプル曲", "Artist", "https://www.youtube.com/watch?v=기본값", "설명", "K-POP", admin);
        }
    }
}