package com.ta.artem.init;

import com.ta.artem.model.Role;
import com.ta.artem.model.User;
import com.ta.artem.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String adminUsername = "admin";

        if (userRepository.findByUsername(adminUsername) == null) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@example.com");
            admin.setRole(Role.ROLE_ADMIN);

            userRepository.save(admin);
            log.info("✅ Админ создан: {}", adminUsername);
        } else {
            log.info("ℹ️ Админ уже существует: {}", adminUsername);
        }

        User manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("manager"));
        manager.setEmail("manager@example.com");
        manager.setRole(Role.ROLE_MANAGER);
        userRepository.save(manager);

    }
}