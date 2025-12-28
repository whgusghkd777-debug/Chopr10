package com.mysite.sbb.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
public class AdminController {

    @GetMapping("/admin")
    public String dashboard() {
        return "admin_dashboard"; // templates/admin_dashboard.html 보여줌
    }
}