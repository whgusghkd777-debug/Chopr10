
// 파일 1: com/mysite/sbb/music/dto/MusicListDto.java (기존 그대로 - 랭킹에도 재사용
package com.mysite.sbb.music.dto;

public record MusicListDto(
        Long id,
        String title,
        String artist,
        String url,
        String content,
        int voteCount,
        int answerCount,
        String thumbnailUrl
) {}