package com.mysite.sbb.answer;

import com.mysite.sbb.music.Music;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

// ★ 아래 이 줄이 꼭 있어야 빨간 줄이 사라집니다!
import com.mysite.sbb.answer.AnswerRepository;

@Service
@RequiredArgsConstructor
public class AnswerService {

    // 이제 이 부분의 빨간 줄이 사라질 거예요.
    private final AnswerRepository answerRepository;

    public void create(Music music, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setMusic(music);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }
}