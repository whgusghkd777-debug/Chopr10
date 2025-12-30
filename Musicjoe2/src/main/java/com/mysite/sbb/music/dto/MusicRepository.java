package com.mysite.sbb.music.dto; // 패키지 경로는 본인 설정에 맞게 확인하세요

import com.mysite.sbb.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    List<Music> findAllByOrderByCreateDateDesc(); // 이 줄을 추가하세요!
}