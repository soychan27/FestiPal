package com.study.festipal.repository;

import com.study.festipal.entity.booth;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoothRepository extends JpaRepository<booth, Integer> {

}
