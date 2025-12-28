package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    // 수동으로 만든 도구들 (롬복 에러를 확실히 잡아줍니다)
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}