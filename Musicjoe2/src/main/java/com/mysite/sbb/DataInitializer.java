package com.mysite.sbb;

import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        // 관리자 계정이 없을 때만 생성
        if (this.userService.getUser("admin") == null) {
            this.userService.create("admin", "admin@musicjoe.com", "admin1234");
            // 注意: UserService.create에서 어드민 권한을 부여하는 로직이 필요할 수 있습니다.
        }
    }
}