package com.ta.artem.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class ManagerController {

    @GetMapping("/hello")
    public String manager() {
        return "manager hello";
    }
}
