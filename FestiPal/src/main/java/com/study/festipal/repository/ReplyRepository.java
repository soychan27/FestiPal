package com.study.festipal.repository;

import com.study.festipal.entity.reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<reply, Integer> {
    List<reply> findAllByBoardId(Integer boardId);
}