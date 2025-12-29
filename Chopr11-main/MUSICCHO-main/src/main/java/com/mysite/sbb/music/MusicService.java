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

    public void like(Music music, SiteUser user) {
        Set<SiteUser> likers = music.getLikers();
        if (likers == null) {
            likers = new HashSet<>();
            music.setLikers(likers);
        }

        if (likers.contains(user)) {
            likers.remove(user);
        } else {
            likers.add(user);
        }
        // 실무 팁: likes 필드는 likers.size()로 동기화하는 것이 가장 정확함
        music.setLikes(likers.size());
        musicRepository.save(music);
    }
}