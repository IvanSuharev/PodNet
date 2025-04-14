package com.podnet.podnet.service;

import com.podnet.podnet.models.User;
import com.podnet.podnet.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSevice {
    UserRepository userRepository;

    public User addUser(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException();
        }
        userRepository.save(user);
        return user;
    }
}
