package com.study.festipal.repository;

import com.study.festipal.entity.board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<board, Integer> {

    Page<board> findByTitleContaining(String searchKeyword, Pageable pageable);
}
