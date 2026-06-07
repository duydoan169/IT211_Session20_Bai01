package org.example.bai1.controller;

import lombok.RequiredArgsConstructor;
import org.example.bai1.model.dto.AuthResponse;
import org.example.bai1.model.dto.LoginDTO;
import org.example.bai1.model.dto.RegisterDTO;
import org.example.bai1.model.entity.Employee;
import org.example.bai1.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public Employee register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDTO request) {
        return userService.login(request);
    }
}