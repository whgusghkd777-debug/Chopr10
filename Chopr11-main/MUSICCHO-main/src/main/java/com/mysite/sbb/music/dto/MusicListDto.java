package com.mysite.sbb.music.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicListDto {
    private Integer id;
    private String title;
    private String artist;
    private String category;
}