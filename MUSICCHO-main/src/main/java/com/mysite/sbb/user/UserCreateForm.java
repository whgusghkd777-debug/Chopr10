package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "ユーザー名は必須です。")
    private String username;

    @NotEmpty(message = "パスワードは必須です。")
    private String password;

    @NotEmpty(message = "パスワード(確認)は必須です。")
    private String passwordConfirm;

    @NotEmpty(message = "メールアドレスは必須です。")
    @Email
    private String email;

    // 직접 작성한 Getter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}