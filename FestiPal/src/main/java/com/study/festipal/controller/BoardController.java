package com.study.festipal.controller;

import com.study.festipal.entity.User;
import com.study.festipal.entity.board;
import com.study.festipal.service.BoardService;
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
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWrite(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }
        return "Board/BoardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(board board, Model model, MultipartFile file, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }

        board.setUser(user);
        board.setBoardcheck(board.getBoardcheck() != null && board.getBoardcheck());
        board.setCreatedTime(LocalDateTime.now());  // 작성 시간 설정
        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());
        return "Board/BoardList";
    }

    @GetMapping("/board/view/{id}")
    public String boardView(@PathVariable("id") Integer id, Model model, HttpSession session) {
        board board = boardService.boardView(id);
        User user = (User) session.getAttribute("user");

        model.addAttribute("board", board);
        model.addAttribute("isAuthor", user != null && user.getId().equals(board.getUser().getId()));
        return "Board/BoardView";
    }

    @PostMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable("id") Integer id, HttpSession session, Model model) {
        board board = boardService.boardView(id);
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getId().equals(board.getUser().getId())) {
            model.addAttribute("message", "삭제 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model, HttpSession session) {
        board board = boardService.boardView(id);
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getId().equals(board.getUser().getId())) {
            model.addAttribute("message", "수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }

        model.addAttribute("board", board);
        return "Board/BoardModify";
    }

    @PostMapping("/board/modifypro/{id}")
    public String boardModifyPro(@PathVariable("id") Integer id, board updateBoard, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getId().equals(updateBoard.getUser().getId())) {
            model.addAttribute("message", "수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }

        board existingBoard = boardService.boardView(id);
        existingBoard.setTitle(updateBoard.getTitle());
        existingBoard.setContent(updateBoard.getContent());
        existingBoard.setBoardcheck(updateBoard.getBoardcheck() != null && updateBoard.getBoardcheck());
        boardService.save(existingBoard);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }
}
