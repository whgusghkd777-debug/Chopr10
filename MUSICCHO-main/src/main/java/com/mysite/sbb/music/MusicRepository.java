package com.mysite.sbb.music;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    // likes가 엔티티에 없으므로, 에러 방지를 위해 기본 조회 기능만 남깁니다.
    List<Music> findAll();
}