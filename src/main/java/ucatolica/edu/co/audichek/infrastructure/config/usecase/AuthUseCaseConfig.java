package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ucatolica.edu.co.audichek.aplication.usecases.auth.AuthUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RolePermissionsRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RolePersonHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.security.JwtUtilComponent;

@Configuration
public class AuthUseCaseConfig {
    @Bean
    public AuthUseCases authUseCases(
            PersonRepository personRepository,
            PasswordEncoder passwordEncoder,
            JwtUtilComponent jwtUtil,
            RolePersonHistoryRepository rolePersonHistoryRepository,
            RolePermissionsRepository rolePermissionsRepository,
            PersonStatusHistoryRepository personStatusHistoryRepository) {

        return new AuthUseCases(
                personRepository,
                passwordEncoder,
                jwtUtil,
                rolePersonHistoryRepository,
                rolePermissionsRepository,
                personStatusHistoryRepository
        );
    }

}
