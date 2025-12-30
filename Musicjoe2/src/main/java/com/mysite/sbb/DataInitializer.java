package com.mysite.sbb;

import com.mysite.sbb.user.UserService;
import com.mysite.sbb.DataNotFoundException; // 예외 처리 추가
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        try {
            // 1. 관리자 계정이 이미 있는지 확인
            this.userService.getUser("admin");
        } catch (DataNotFoundException e) {
            // 2. 관리자 계정이 없을 때만 새로 생성
            // 팁: 비밀번호를 '1234'보다 조금 더 복잡하게 설정하는 것이 좋습니다.
            this.userService.create("admin", "admin@musicjoe.com", "1234");
            
            // 3. 주의: pgAdmin에서 수동으로 'ROLE_ADMIN'을 넣어주었던 것처럼,
            // 실제 운영 환경에서는 서비스 코드 안에서도 권한을 부여하는 로직이 있으면 좋습니다.
            System.out.println("=== 管理者アカウント(admin)が生成されました ===");
        }
    }
}