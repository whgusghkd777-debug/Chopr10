package com.mysite.sbb.music;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        return music.orElseThrow(() -> new DataNotFoundException("Music not found"));
    }

    // [修正] DataInitializer가 요구하는 6개 인자 형식에 맞춤
    public Music create(String title, String artist, String content, String url, String category, SiteUser user) {
        Music music = new Music();
        music.setTitle(title);
        music.setArtist(artist);
        music.setContent(content);
        music.setUrl(url);
        music.setCategory(category);
        music.setAuthor(user);
        music.setCreateDate(LocalDateTime.now());
        music.setLikes(0);
        music.setLikers(new HashSet<>());
        return this.musicRepository.save(music);
    }

    public void like(Music music, SiteUser user) {
        Set<SiteUser> likers = music.getLikers();
        if (likers.contains(user)) {
            likers.remove(user);
        } else {
            likers.add(user);
        }
        music.setLikes(likers.size());
        this.musicRepository.save(music);
    }
}