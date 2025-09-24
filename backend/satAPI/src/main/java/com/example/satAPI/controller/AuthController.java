package com.example.satAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.satAPI.dto.LoginRequest;
import com.example.satAPI.dto.LoginResponse;
import com.example.satAPI.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.autenticar(request);
        if (response == null) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
        return ResponseEntity.ok(response);
    }
}
