package com.mysite.sbb.music;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private String content;  // 설명이나 description

    private String url;  // YouTube나 파일 링크

    private String category;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "music", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    // 새로 추가: 좋아요
    private Integer likes = 0;

    @ManyToMany
    private Set<SiteUser> likers;
}