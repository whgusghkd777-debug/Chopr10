package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionService questionService;

    @Test
    void testJpa() {
        this.questionService.create("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        this.questionService.create("스프リング부트 모델 질문입니다.", "id는 자동으로 생성되나요?");

        List<Question> all = this.questionService.getList();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
}