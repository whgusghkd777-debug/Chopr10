package com.mysite.sbb.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {

    // 랭킹: 좋아요 많은 순
    @Query("SELECT m FROM Music m ORDER BY m.likes DESC")
    List<Music> findAllOrderByLikesDesc();
}