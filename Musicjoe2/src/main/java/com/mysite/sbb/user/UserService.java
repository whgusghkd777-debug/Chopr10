package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

  
    public SiteUser create(String username, String email, String password) {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    
    // 아이디가 'admin'이면 관리자 권한 부여, 아니면 일반 유저
    if ("admin".equals(username)) {
        user.setRole(UserRole.ADMIN);
    } else {
        user.setRole(UserRole.USER);
    }
    
    this.userRepository.save(user);
    return user;
}
    // ユーザー検索
    public SiteUser getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}