package com.study.festipal.repository;

import com.study.festipal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByIdAndPassword(String id, String password);
}