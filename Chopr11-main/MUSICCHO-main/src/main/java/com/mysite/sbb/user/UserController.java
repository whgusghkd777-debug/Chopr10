package com.mysite.sbb.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        // [修正] ファイル名に合わせて変更
        return "user/login_fragment"; 
    }

    @GetMapping("/signup")
    public String signup() {
        // [修正] ファイル名に合わせて変更
        return "user/signup_fragment"; 
    }
}