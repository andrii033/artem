package com.ta.artem.controllers;

import com.ta.artem.model.Role;
import com.ta.artem.model.User;
import com.ta.artem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/create")
    public String createUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin")); // Hashing password
        user.setEmail("newEmail");
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
        log.info("User created");
        return "createUser";
    }
}
