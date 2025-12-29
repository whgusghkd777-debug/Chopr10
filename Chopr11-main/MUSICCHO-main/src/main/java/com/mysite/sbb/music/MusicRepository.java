package com.mysite.sbb.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
    // 여기서 <Music, Integer>의 Integer가 findById의 매개변수 타입을 결정합니다.
}