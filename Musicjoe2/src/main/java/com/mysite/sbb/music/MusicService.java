package com.mysite.sbb.music;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.music.dto.MusicListDto;
import com.mysite.sbb.music.dto.MusicRepository;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 추가하면 안전합니다.

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public List<MusicListDto> getList() {
        List<Music> musicList = this.musicRepository.findAllByOrderByCreateDateDesc();
        return musicList.stream()
                .map(music -> new MusicListDto(
                    music.getId(), 
                    music.getTitle(), 
                    music.getArtist(), 
                    music.getThumbnailUrl(), 
                    music.getCreateDate(),
                    music.getVoter() != null ? music.getVoter().size() : 0))
                .collect(Collectors.toList());
    }

    public void create(String title, String artist, String url, String content, SiteUser author) {
        Music m = new Music();
        m.setTitle(title);
        m.setArtist(artist);
        m.setUrl(url);
        m.setContent(content);
        m.setAuthor(author);
        m.setCreateDate(LocalDateTime.now());
        
        if (url != null && url.contains("v=")) {
            try {
                String videoId = url.split("v=")[1].split("&")[0];
                m.setThumbnailUrl("https://img.youtube.com/vi/" + videoId + "/mqdefault.jpg");
            } catch (Exception e) {
                m.setThumbnailUrl(null);
            }
        }
        this.musicRepository.save(m);
    }

    public Music getMusic(Integer id) {
        return this.musicRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("music not found"));
    }

    @Transactional // 추천 기능은 데이터가 변하므로 붙여주는 것이 좋습니다.
    public void vote(Music music, SiteUser siteUser) {
        music.getVoter().add(siteUser);
        this.musicRepository.save(music);
    }

    public void delete(Music music) {
        this.musicRepository.delete(music);
    }
}