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

    // 목록
    public List<Music> getList() {
        return this.musicRepository.findAll();
    }

    // 단건 조회 (Integer로 통일)
    public Music getMusic(Integer id) {
        return this.musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("音楽が見つかりません"));
    }

    // 생성
    public void create(String title, String artist, String url, String content, String category, SiteUser author) {
        Music music = new Music();
        music.setTitle(title);
        music.setArtist(artist);
        music.setUrl(url);
        music.setContent(content);
        music.setCategory(category);
        music.setAuthor(author);
        music.setCreateDate(LocalDateTime.now());
        music.setLikes(0);
        this.musicRepository.save(music);
    }

    // 좋아요 (임시 +1)
    public boolean toggleLike(Integer musicId, SiteUser user) {
        Music music = getMusic(musicId);
        music.setLikes(music.getLikes() + 1);
        this.musicRepository.save(music);
        return true;
    }

    public int getLikesCount(Integer musicId) {
        Music music = getMusic(musicId);
        return music.getLikes();
    }

    // 삭제
    public void delete(Integer id) {
        Music music = getMusic(id);
        this.musicRepository.delete(music);
    }
}