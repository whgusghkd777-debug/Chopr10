package com.mysite.sbb.music.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicListDto {
    // 실무 포인트: 엔티티의 ID 타입과 DTO의 ID 타입은 반드시 일치시켜야 합니다.
    private Integer id; 
    private String title;
    private String artist;
    private String category;
}