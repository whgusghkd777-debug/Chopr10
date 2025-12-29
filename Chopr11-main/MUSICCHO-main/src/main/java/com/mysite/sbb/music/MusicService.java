package com.mysite.sbb.music;

import com.mysite.sbb.music.dto.MusicListDto;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public List<MusicListDto> getList() {
        List<Music> musicList = this.musicRepository.findAll();
        return musicList.stream().map(music -> {
            MusicListDto dto = new MusicListDto();
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