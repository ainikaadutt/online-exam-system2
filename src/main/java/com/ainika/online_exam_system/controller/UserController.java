package com.ainika.online_exam_system.controller;

import com.ainika.online_exam_system.entity.User;
import com.ainika.online_exam_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}