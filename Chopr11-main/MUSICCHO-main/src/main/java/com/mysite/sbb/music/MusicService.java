package com.mysite.sbb.music;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.mysite.sbb.music.dto.MusicListDto;
import com.mysite.sbb.user.SiteUser;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public List<MusicListDto> getList() {
        List<Music> musicList = this.musicRepository.findAll();
        return musicList.stream().map(music -> {
            MusicListDto dto = new MusicListDto();
            // MusicListDto의 id가 Integer이므로 이제 에러가 나지 않습니다.
            dto.setId(music.getId()); 
            dto.setTitle(music.getTitle());
            dto.setArtist(music.getArtist());
            dto.setCategory(music.getCategory());
            return dto;
        }).collect(Collectors.toList());
    }

    public Music getMusic(Integer id) {
        return this.musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Music not found"));
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
        this.musicRepository.save(music);
    }
}