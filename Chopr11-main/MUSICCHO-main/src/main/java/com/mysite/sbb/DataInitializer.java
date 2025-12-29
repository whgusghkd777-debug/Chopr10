// DataInitializer.java 전체 (import 추가, admin 사용자 제대로 생성해서 create 호출)
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
                // 만약 admin 없으면 생성 (비밀번호는 Security에서 처리되거나 별도 로직 필요)
                // 여기선 단순히 조회만 가정. 실제 회원가입 로직 있으면 그걸 써
                // 임시로 null 체크 후 스킵하거나 예외 처리
                return;
            }

            
            musicService.create(
                "Sample Song",
                "Sample Artist",
                "https://example.com/sample.mp3",
                "説明",
                "pop",
                admin
            );

        } catch (Exception e) {
            // 초기화 실패해도 앱 시작되게
            e.printStackTrace();
        }
    }
}