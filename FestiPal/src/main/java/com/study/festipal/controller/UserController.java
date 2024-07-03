package com.study.festipal.controller;

import com.study.festipal.entity.User;
import com.study.festipal.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

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
    public String UserLoginPro(String id, String password, Model model, HttpSession session){
        User user = userService.findById(id);
        if (user != null && user.getPassword().equals(password)){
            session.setAttribute("user",user);
            model.addAttribute("message", "로그인 성공하였습니다.");
            model.addAttribute("searchUrl", "/main");
        } else {
            model.addAttribute("message", "로그인에 실패하였습니다. 아이디 또는 비밀번호를 확인해주세요.");
            model.addAttribute("searchUrl", "/user/login");
        }

        return "message";

    }

    @GetMapping("/user/logout")
    public String UserLogout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("message", "로그아웃 되었습니다.");
        model.addAttribute("searchUrl", "/main");
        return "message";
    }
}
