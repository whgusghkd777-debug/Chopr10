package com.mysite.sbb.answer;

import com.mysite.sbb.music.Music;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
    @ManyToOne
    private SiteUser author;
    @ManyToOne
    private Music music;

    // 수동 Getter/Setter (롬복 에러 방지용)
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public void setAuthor(SiteUser author) { this.author = author; }
    public void setMusic(Music music) { this.music = music; }
    public Music getMusic() { return music; }
}