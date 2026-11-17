package com.example.jwtproject;

import com.example.jwtproject.entity.Role;
import com.example.jwtproject.entity.User;
import com.example.jwtproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

    /**
     * Seeds initial demo users (ADMIN and USER) if the database is fresh.
     */
    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User(
                        "admin",
                        "admin@example.com",
                        passwordEncoder.encode("Admin@123"),
                        Role.ADMIN
                );
                userRepository.save(admin);
                System.out.println(">>> Seeded default ADMIN user: username='admin', password='Admin@123'");
            }

            if (!userRepository.existsByUsername("root")) {
                User rootAdmin = new User(
                        "root",
                        "root@example.com",
                        passwordEncoder.encode("Kavitha@123"),
                        Role.ADMIN
                );
                userRepository.save(rootAdmin);
                System.out.println(">>> Seeded default ROOT ADMIN: username='root', password='Kavitha@123'");
            }

            if (!userRepository.existsByUsername("user")) {
                User standardUser = new User(
                        "user",
                        "user@example.com",
                        passwordEncoder.encode("User@123"),
                        Role.USER
                );
                userRepository.save(standardUser);
                System.out.println(">>> Seeded default USER: username='user', password='User@123'");
            }
        };
    }
}
