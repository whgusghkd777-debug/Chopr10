package com.mysite.sbb.music;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String artist;
    private String content;
    private String url;
    private String category;
    private LocalDateTime createDate;

    @ManyToOne
    private SiteUser author;

    // [중요] 좋아요 숫자 저장
    private Integer likes = 0;

    // [중요] 좋아요 누른 유저 목록
    @ManyToMany
    private Set<SiteUser> likers = new HashSet<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}