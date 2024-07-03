package com.study.festipal.controller;

import com.study.festipal.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            model.addAttribute("username", loggedUser.getId());
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
        return "Main";
    }
}
