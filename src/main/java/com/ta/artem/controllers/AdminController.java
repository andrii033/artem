package com.ta.artem.controllers;

import com.ta.artem.dto.CreateUserDTO;
import com.ta.artem.dto.UserDTO;
import com.ta.artem.model.Role;
import com.ta.artem.model.User;
import com.ta.artem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/users")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/create")
    public String createUser(@RequestBody CreateUserDTO createUserDTO) {

        if (userService.findeUserByUsername(createUserDTO.getUsername()) != null) {
            return "User already exists";
        } else {
            User user = new User();
            user.setUsername(createUserDTO.getUsername());
            user.setRole(Role.valueOf(createUserDTO.getRole()));
            user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
            user.setEmail(createUserDTO.getEmail());

            userService.createUser(user);
            return "User created successfully";
        }
    }

    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
