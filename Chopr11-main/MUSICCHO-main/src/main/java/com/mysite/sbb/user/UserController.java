package com.mysite.sbb.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(String username, String email, String password, String passwordConfirm, Model model) {
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "パスワードが一致しません。");
            return "user/signup";
        }
        // TODO: 회원가입 서비스 로직 추가 (DB 저장)
        return "redirect:/user/login";
    }
}