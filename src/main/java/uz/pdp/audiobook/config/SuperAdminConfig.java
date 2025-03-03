package uz.pdp.audiobook.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.enums.Role;
import uz.pdp.audiobook.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;

import static uz.pdp.audiobook.utils.AuthConstants.*;

@RequiredArgsConstructor
@Configuration
public class SuperAdminConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User user = new User();

        if (userRepository.findByUsernameAndDeletedFalse(SUPER_ADMIN_GMAIL).isEmpty()) {
            user.setUsername(SUPER_ADMIN_GMAIL);
            user.setPassword(passwordEncoder.encode(SUPER_PASSWORD));
            user.setRole(Role.SUPER_ADMIN);
            user.setDateOfBirth(Date.valueOf(LocalDate.of(1990, 1, 1)));
            user.setFirstName(SUPER_ADMIN);
            user.setLastName(SUPER_ADMIN);
            userRepository.save(user);

            System.out.println("SUPER ADMIN CREATED");

        }
    }
}
