package com.podnet.podnet.controller;

import com.podnet.podnet.models.User;
import com.podnet.podnet.schemas.FormRegistration;
import com.podnet.podnet.service.UserSevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    UserSevice userSevice;

    public UserController(UserSevice userSevice) {
        this.userSevice = userSevice;
    }

    @PostMapping("/user/")
    public User addUser(@RequestBody FormRegistration formRegistration) {
        return userSevice.addUser(formRegistration);
    }
}
