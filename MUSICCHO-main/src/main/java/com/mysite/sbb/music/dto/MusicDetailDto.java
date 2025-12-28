package com.mysite.sbb.music.dto;

import com.mysite.sbb.music.Music;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDetailDto {
    private Long id;
    private String title;
    private String artist;
    private String url;
    private String content;
    private int likes;
    private String author;

    public MusicDetailDto(Music m) {
        this.id = m.getId();
        this.title = m.getTitle();
        this.artist = m.getArtist();
        this.url = m.getUrl();
        this.content = m.getContent();
        this.likes = m.getVoter() != null ? m.getVoter().size() : 0;
        this.author = m.getAuthor() != null ? m.getAuthor().getUsername() : "Anonymous";
    }
}