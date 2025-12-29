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
    public void run(String... args) {
        try {
            SiteUser admin = userService.getUser("admin");
            if (admin == null) {
                admin = userService.create("admin", "admin@test.com", "1234");
            }

            // 중복 체크 (이미 데이터 있으면 안 넣음)
            if (musicService.getList().isEmpty()) {
                musicService.create(
                    "Sample Song",
                    "Sample Artist",
                    "https://example.com/sample.mp3",
                    "샘플 음악 설명",
                    "POP",
                    admin
                );
            }
        } catch (Exception e) {
            // 초기 데이터 실패해도 앱 죽지 않게 로그만
            System.err.println("DataInitializer 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}