package com.mysite.sbb.music;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.*;

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

    private String url;

    private String category;

    private LocalDateTime createDate;

    private int likes = 0;

    @ManyToOne
    private SiteUser author;

    @OneToMany(mappedBy = "music", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    // 직접 getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public SiteUser getAuthor() { return author; }
    public void setAuthor(SiteUser author) { this.author = author; }

    public List<Answer> getAnswerList() { return answerList; }
    public void setAnswerList(List<Answer> answerList) { this.answerList = answerList; }
}