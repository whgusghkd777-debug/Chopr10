package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // [修正] findByusername -> findByUsername (Uは大文字)
    Optional<SiteUser> findByUsername(String username);
}