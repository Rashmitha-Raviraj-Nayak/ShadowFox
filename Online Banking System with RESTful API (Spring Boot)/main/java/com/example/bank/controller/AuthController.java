package com.example.bank.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final com.example.bank.service.UserService userService;

    public AuthController(com.example.bank.service.UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public com.example.bank.dto.response.AuthResponseDTO login(@RequestBody com.example.bank.dto.request.AuthRequestDTO dto) {
        boolean ok = userService.authenticate(dto.getUsername(), dto.getPassword());
        if (ok) {
            // token generation skipped
            com.example.bank.dto.response.AuthResponseDTO resp = new com.example.bank.dto.response.AuthResponseDTO();
            resp.setUsername(dto.getUsername());
            resp.setToken("dummy-token");
            return resp;
        } else {
            throw new com.example.bank.exception.ResourceNotFoundException("Invalid credentials");
        }
    }
} 
