package com.ainika.online_exam_system.service;

import com.ainika.online_exam_system.entity.User;
import com.ainika.online_exam_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}