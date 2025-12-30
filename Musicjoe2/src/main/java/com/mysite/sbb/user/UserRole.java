package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    // 반드시 DB에 넣은 글자(ROLE_ADMIN)와 똑같은 이름이 여기 있어야 합니다.
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ROLE_ADMIN("ROLE_ADMIN"); // 에러 방지를 위해 이 줄을 추가합니다.

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}