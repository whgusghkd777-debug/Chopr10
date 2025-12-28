package com.mysite.sbb.question;

import org.springframework.data.jpa.repository.JpaRepository;

// Integer를 Long으로 수정하세요.
public interface QuestionRepository extends JpaRepository<Question, Long> {
}