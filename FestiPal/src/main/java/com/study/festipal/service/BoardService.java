package com.study.festipal.service;

import com.study.festipal.entity.board;
import com.study.festipal.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(board board, MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            if (!Files.exists(Paths.get(projectPath))) {
                Files.createDirectories(Paths.get(projectPath));
            }

            UUID uuid = UUID.randomUUID();
            String imagename = uuid + "_" + file.getOriginalFilename();
            File savefile = new File(projectPath, imagename);
            file.transferTo(savefile);

            board.setImagename(imagename);
            board.setImagepath("/files/" + imagename);
        }

        boardRepository.save(board);
    }

    @GetMapping("/board/list")
    public List<board> boardList() {
        return boardRepository.findAll();
    }

    public board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    public void save(board board) {
        boardRepository.save(board);
    }
}
