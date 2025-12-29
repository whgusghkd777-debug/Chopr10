package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // [중요] findByUsername으로 U를 대문자로 수정
    Optional<SiteUser> findByUsername(String username);
}