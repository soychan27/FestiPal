package com.study.festipal.controller;

import com.study.festipal.entity.User;
import com.study.festipal.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //회원 가입
    @GetMapping("/user/join")
    public String User() {
        return "User/UserJoin";
    }

    //로그인 창으로
    @GetMapping("/user/login")
    public String UserLogin() {
        return "User/UserLogin";
    }

    //회원 가입 처리
    @PostMapping("/user/joinpro")
    public String UserJoinPro(User user, Model model) {
        userService.join(user);

        model.addAttribute("message", "회원 가입이 완료되었습니다.");
        model.addAttribute("searchUrl", "/main");
        return "message";
    }

    //로그인 처리
    @PostMapping("/user/loginpro")
    public String userLoginPro(User user, HttpSession session, Model model) {
        User loginUser = userService.login(user);
        if (loginUser != null) {
            session.setAttribute("user", loginUser);
            model.addAttribute("message", "로그인 성공");
            model.addAttribute("searchUrl", "/main");
            return "message";
        } else {
            model.addAttribute("message", "로그인 실패");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }
    }

    @GetMapping("/user/logout")
    public String UserLogout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("message", "로그아웃 되었습니다.");
        model.addAttribute("searchUrl", "/main");
        return "message";
    }
}
