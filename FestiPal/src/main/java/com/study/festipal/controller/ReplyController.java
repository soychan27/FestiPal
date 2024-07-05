package com.study.festipal.controller;

import com.study.festipal.entity.User;
import com.study.festipal.entity.reply;
import com.study.festipal.entity.board;
import com.study.festipal.service.ReplyService;
import com.study.festipal.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/reply/add")
    public String addReply(@RequestParam("boardid") Integer boardId,
                           @RequestParam("content") String content,
                           HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }

        board board = boardService.boardView(boardId);
        reply reply = new reply();
        reply.setUser(user);
        reply.setBoard(board);
        reply.setContent(content);
        reply.setCreatedTime(LocalDateTime.now());

        replyService.save(reply);

        return "redirect:/board/view/" + boardId;
    }

    @PostMapping("/reply/delete/{id}")
    public String deleteReply(@PathVariable("id") Integer id, HttpSession session, Model model) {
        reply reply = replyService.findById(id);
        User user = (User) session.getAttribute("user");

        if (user == null || (!user.getId().equals(reply.getUser().getId()) && user.getAuthority() != 2)) {
            model.addAttribute("message", "삭제 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }

        replyService.delete(id);
        return "redirect:/board/view/" + reply.getBoard().getId();
    }
}
