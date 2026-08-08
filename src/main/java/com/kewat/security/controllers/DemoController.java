package com.kewat.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return "admin";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {

        System.out.println(authentication);

        if (authentication == null) {
            return "Authentication is NULL";
        }

        return authentication.getName() + " -> " + authentication.getAuthorities();
    }
}
