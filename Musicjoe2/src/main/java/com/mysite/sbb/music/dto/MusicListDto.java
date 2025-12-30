package com.mysite.sbb.music.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class MusicListDto {
    private Integer id;
    private String title;
    private String artist;
    private String thumbnailUrl;
    private LocalDateTime createDate;
    private Integer voteCount; // 추천수를 저장할 칸 추가

    public MusicListDto(Integer id, String title, String artist, String thumbnailUrl, LocalDateTime createDate, Integer voteCount) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.thumbnailUrl = thumbnailUrl;
        this.createDate = createDate;
        this.voteCount = (voteCount == null) ? 0 : voteCount; // null이면 0으로 설정
    }
}