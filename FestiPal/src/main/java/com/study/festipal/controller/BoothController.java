package com.study.festipal.controller;


import com.study.festipal.entity.User;
import com.study.festipal.entity.booth;
import com.study.festipal.service.BoothService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
public class BoothController {
    @Autowired
    private BoothService boothService;

    @GetMapping("/booth/list")
    public String boothList(Model model){
        model.addAttribute("list", boothService.boothList());
        return "Booth/BoothList";
    }

    @GetMapping("/booth/write")
    public String boothWrite(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if(user == null || user.getAuthority()==0) {
            model.addAttribute("message", "글 작성 권한이 없습니다.");
            model.addAttribute("searchUrl", "/booth/list");
            return "message";
        }
        return "Booth/BoothWrite";
    }

    @PostMapping("/booth/writepro")
    public String boothWritePro(booth booth, Model model, MultipartFile file, HttpSession session ) throws Exception{
        User user = (User) session.getAttribute("user");
        if (user == null ){
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }
        else if(user.getAuthority()==0){
            model.addAttribute("message", "권한이 없습니다.");
            model.addAttribute("searchUrl", "/booth/list");
            return "message";
        }

        booth.setUser(user);
        booth.setCreatedTime(LocalDateTime.now());
        boothService.write(booth, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/booth/list");
        return "message";
    }

    @GetMapping("/booth/view/{id}")
    public String boothView(@PathVariable("id") Integer id, Model model, HttpSession session){
        booth booth = boothService.boothView(id);
        User user = (User) session.getAttribute("user");

        model.addAttribute("booth", booth);
        model.addAttribute("isAuthor", user != null && user.getId().equals(booth.getUser().getId()));
        model.addAttribute("isAdmin", user!=null && user.getAuthority() == 2);
        return "Booth/BoothView";
    }

    @PostMapping("/booth/delete/{id}")
    public String boothDelete(@PathVariable("id") Integer id, HttpSession session, Model model) {
        booth booth = boothService.boothView(id);
        User user = (User) session.getAttribute("user");

        if (user == null || (!user.getId().equals(booth.getUser().getId()) && user.getAuthority() != 2)) {
            model.addAttribute("message", "삭제 권한이 없습니다.");
            model.addAttribute("searchUrl", "/booth/list");
            return "message";
        }

        boothService.boothDelete(id);
        return "redirect:/booth/list";
    }

    @GetMapping("/booth/modify/{id}")
    public String boothModify(@PathVariable("id") Integer id, Model model, HttpSession session) {
        booth booth = boothService.boothView(id);
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getId().equals(booth.getUser().getId())) {
            model.addAttribute("message", "수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/booth/list");
            return "message";
        }

        model.addAttribute("booth", booth);
        return "Booth/BoothModify";
    }

    @PostMapping("/booth/modifypro/{id}")
    public String boothModifyPro(@PathVariable("id") Integer id, booth updateBooth, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getId().equals(updateBooth.getUser().getId())) {
            model.addAttribute("message", "수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/booth/list");
            return "message";
        }

        booth existingBooth = boothService.boothView(id);
        existingBooth.setTitle(updateBooth.getTitle());
        existingBooth.setContent(updateBooth.getContent());
        boothService.save(existingBooth);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/booth/list");
        return "message";
    }
}
