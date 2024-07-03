package com.study.festipal.controller;

import com.study.festipal.entity.board;
import com.study.festipal.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String board() {
        return "Board/BoardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(board board, Model model, MultipartFile file) throws Exception {

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList() {
        return "Board/BoardList";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        model.addAttribute("board",boardService.boardView(id));
        return "redirect:/board/list";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {
        model.addAttribute("board",boardService.boardView(id));

        return "Board/BoardModify";
    }

}
