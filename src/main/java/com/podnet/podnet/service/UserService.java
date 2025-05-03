package com.podnet.podnet.service;

import com.podnet.podnet.models.User;
import com.podnet.podnet.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
