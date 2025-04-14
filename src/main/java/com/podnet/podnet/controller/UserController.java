package com.podnet.podnet.controller;

import com.podnet.podnet.models.User;
import com.podnet.podnet.service.UserSevice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    UserSevice userSevice;

    public UserController(UserSevice userSevice) {
        this.userSevice = userSevice;
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userSevice.addUser(user);
    }
}
