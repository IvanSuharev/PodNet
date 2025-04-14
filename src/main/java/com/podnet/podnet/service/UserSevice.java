package com.podnet.podnet.service;

import com.podnet.podnet.models.User;
import com.podnet.podnet.repository.UserRepository;

import com.podnet.podnet.schemas.FormRegistration;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSevice {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserSevice(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(FormRegistration formRegistration) {
        User user = formRegistration.createUser(passwordEncoder);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username is existed");
        }
        userRepository.save(user);
        return user;
    }
}
