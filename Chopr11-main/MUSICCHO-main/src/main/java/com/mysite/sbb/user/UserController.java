package com.mysite.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        // [修正] 実際のファイル名に合わせて変更
        return "user/login_fragment"; 
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup_fragment"; 
    }

    @PostMapping("/signup")
    public String signup(String username, String email, String password, String passwordConfirm, Model model) {
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "パスワードが一致しません。");
            return "user/signup_fragment";
        }
        userService.create(username, email, password);
        return "redirect:/user/login";
    }
}