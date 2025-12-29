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
            // 1. admin 사용자 생성 또는 조회
            SiteUser admin = userService.getUser("admin");
            if (admin == null) {
                admin = userService.create("admin", "admin@test.com", "1234");
            }

            // 2. 음악 데이터 중복 체크 후 삽입
            if (musicService.getList().isEmpty()) {
                musicService.create(
                    "Sample Song",
                    "Sample Artist",
                    "https://example.com/sample.mp3",
                    "샘플 음악 설명입니다.",
                    "POP",
                    admin
                );
            }
        } catch (Exception e) {
            // 초기 데이터 삽입 실패해도 앱은 계속 실행되게
            System.err.println("DataInitializer 실행 중 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }
}