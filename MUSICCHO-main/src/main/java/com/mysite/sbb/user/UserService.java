package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 新規ユーザー作成
   public SiteUser create(String username, String email, String password) {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setRole(UserRole.USER); // ✅ 기본 권한을 꼭 넣어주어야 에러가 안 납니다.
    this.userRepository.save(user);
    return user;
}

    // ユーザー検索
    public SiteUser getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}