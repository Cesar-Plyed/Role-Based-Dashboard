package dev.BSC.auth_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.BSC.auth_service.entity.User;
import dev.BSC.auth_service.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            // Creamos los usuarios base automáticamente
            createUser("admin", "123456", "ADMIN");
            createUser("user", "123456", "USER");
            createUser("supervisor", "123456", "SUPERVISOR");
        }
    }

    private void createUser(String username, String pass, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(pass));
        user.setRole(role);
        userRepository.save(user);
    }
}