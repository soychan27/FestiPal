package com.study.festipal.service;

import com.study.festipal.entity.board;
import com.study.festipal.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String imagename = uuid + "_" + file.getOriginalFilename();

        File savefile = new File(projectPath, imagename);

        file.transferTo(savefile);

        board.setImagename(imagename);
        board.setImagepath("/files/" + imagename);

        boardRepository.save(board);


    }

    @GetMapping("/board/list")
    public Page<board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}
