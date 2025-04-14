package com.podnet.podnet.schemas;

import com.podnet.podnet.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class FormRegistration {
    private String username;
    private String password;
    private String email;
    private String description;
    private String phoneNumber;

    public FormRegistration(String username, String password, String email, String description, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public User createUser(PasswordEncoder passwordEncoder) {
        System.out.println(password);
        return new User(username, passwordEncoder.encode(password), email, description, phoneNumber);
    }
}
