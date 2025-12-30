package com.mysite.sbb.music;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(length = 100)
    private String artist;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String url; // 유튜브 URL
    private String filePath; // 서버에 저장된 음악 파일 경로 (추가)
    private String category;
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "music", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;
    // Music.java 파일 안에 추가
private String thumbnailUrl;
    @ManyToMany
    private Set<SiteUser> voter; // 랭킹 시스템을 위한 추천인 리스트
}