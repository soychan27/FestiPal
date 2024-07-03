package com.study.festipal.service;

import com.study.festipal.entity.User;
import com.study.festipal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void join(User user) {
        userRepository.save(user);
    }

    public User findById(String id){
        return userRepository.findById(id).orElse(null);
    }
}
