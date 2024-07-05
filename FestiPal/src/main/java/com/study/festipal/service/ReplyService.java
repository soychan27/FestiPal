package com.study.festipal.service;

import com.study.festipal.entity.reply;
import com.study.festipal.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public void save(reply reply) {
        replyRepository.save(reply);
    }

    public void delete(Integer id) {
        replyRepository.deleteById(id);
    }

    public reply findById(Integer id) {
        return replyRepository.findById(id).orElse(null);
    }

    public List<reply> findAllByBoardId(Integer boardId) {
        return replyRepository.findAllByBoardId(boardId);
    }
}
