// MusicService.java 전체 (import java.util.Set 추가, likers 초기화 안전하게)
package com.mysite.sbb.music;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;  // 이 import 추가 필수!
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public List<Music> getList() {
        return musicRepository.findAllOrderByLikesDesc();
    }

    public Music getMusic(Integer id) {
        Optional<Music> music = musicRepository.findById(id);
        return music.orElseThrow(() -> new DataNotFoundException("music not found"));
    }

    public Music create(String title, String artist, String content, String url, String category, SiteUser user) {
        Music music = new Music();
        music.setTitle(title);
        music.setArtist(artist);
        music.setContent(content);
        music.setUrl(url);
        music.setCategory(category);
        music.setCreateDate(LocalDateTime.now());
        music.setAuthor(user);
        music.setLikes(0);

        // likers 초기화 (null 방지)
        if (music.getLikers() == null) {
            music.setLikers(new HashSet<>());
        }

        musicRepository.save(music);
        return music;
    }

    public void like(Music music, SiteUser user) {
        Set<SiteUser> likers = music.getLikers();
        if (likers == null) {
            likers = new HashSet<>();
            music.setLikers(likers);
        }

        if (likers.contains(user)) {
            likers.remove(user);
            music.setLikes(music.getLikes() - 1);
        } else {
            likers.add(user);
            music.setLikes(music.getLikes() + 1);
        }
        musicRepository.save(music);
    }
}