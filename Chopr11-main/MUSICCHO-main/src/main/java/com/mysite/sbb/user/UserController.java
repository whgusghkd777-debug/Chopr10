package com.mysite.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "user/login_fragment"; // 파일명과 일치시킴
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup_fragment"; // 파일명과 일치시킴
    }
}