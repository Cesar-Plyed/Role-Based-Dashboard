package dev.BSC.auth_service.service;

import dev.BSC.auth_service.entity.User;
import dev.BSC.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // De Spring Security
    private final JwtService jwtService; // Clase auxiliar para crear el token

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registro: Crea usuario y encripta contraseña
    public User register(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar usuario");
        }
    }

    // Login: Verifica password y devuelve AuthResponse con Token y Metadata
    public dev.BSC.auth_service.dto.AuthResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password incorrecto");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return dev.BSC.auth_service.dto.AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}