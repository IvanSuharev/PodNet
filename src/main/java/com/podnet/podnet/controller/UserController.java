package com.podnet.podnet.controller;

import com.podnet.podnet.models.User;
import com.podnet.podnet.service.UserSevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {
    UserSevice userSevice;
    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userSevice.addUser(user);
    }
}
