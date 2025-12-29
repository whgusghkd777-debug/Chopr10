package com.mysite.sbb.music;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public List<Music> getList() {
        return this.musicRepository.findAll();
    }

    public Music getMusic(Integer id) {
        return this.musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("音楽が見つかりません"));
    }

    public void create(String title, String artist, String url, String content, String category, SiteUser author) {
        Music music = new Music();
        music.setTitle(title);
        music.setArtist(artist);
        music.setUrl(url);
        music.setContent(content); 
        music.setCategory(category);
        music.setAuthor(author);
        music.setCreateDate(LocalDateTime.now());
        music.setLikes(0);  // 초기화
        this.musicRepository.save(music);
    }

    // 좋아요 토글 (간단 +1/-1, 실제론 Set<SiteUser> likers로 중복 체크 추천)
    public boolean toggleLike(Integer musicId, SiteUser user) {
        Music music = getMusic(musicId);
        // 중복 체크 로직 추가 가능 (likers.contains(user) 등)
        music.setLikes(music.getLikes() + 1);  // 임시로 항상 +1 (실제론 토글)
        this.musicRepository.save(music);
        return true;  // 좋아요 상태 (true = 눌림)
    }

    public int getLikesCount(Integer musicId) {
        Music music = getMusic(musicId);
        return music.getLikes();
    }

    public void delete(Integer id) {
        Music music = getMusic(id);
        this.musicRepository.delete(music);
    }
}