package dev.BSC.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.BSC.auth_service.entity.User;
import dev.BSC.auth_service.service.AuthService;

@RestController
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user.getUsername(), user.getPassword(), user.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<dev.BSC.auth_service.dto.AuthResponse> login(
            @RequestBody dev.BSC.auth_service.dto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }
}
