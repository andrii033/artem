package com.ta.artem.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(@RequestHeader("Authorization") String authorizationHeader) {
        // Print the Authorization header (should include Bearer token)
        System.out.println("Authorization Header: " + authorizationHeader);

        return "Welcome Admin";
    }
}
