package com.example.identityservice.controller;

import com.example.identityservice.dto.*;
import com.example.identityservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    /** Trả về { accessToken, refreshToken, tokenType, expiresIn }. */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /** Public (permitAll) vì lúc gọi API này access token thường đã hết hạn. Body: { "refreshToken": "<uuid>" } */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.refreshToken()));
    }

    @GetMapping("/test-token")
    public ResponseEntity<TokenResponse> testToken(@RequestParam String username) {
        return ResponseEntity.ok(authService.generateTestToken(username));
    }
}
