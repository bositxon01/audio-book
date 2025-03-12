package uz.pdp.audiobook.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.enums.Role;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.utils.PasswordGenerator;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public OAuth2SuccessHandler(UserRepository userRepository, JWTProvider jwtProvider, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name"); // Given Name from Google
        String lastName = oAuth2User.getAttribute("family_name"); // Family Name from Google

        if (email == null) {
            throw new IllegalStateException("Email not found in OAuth provider response");
        }

        // Find or create user
        User user = userRepository.findByUsernameAndDeletedFalse(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    String password = PasswordGenerator.generateRandomPassword();

                    newUser.setUsername(email);
                    newUser.setPassword(passwordEncoder.encode(password)); // Ensure password is not blank
                    newUser.setFirstName(firstName != null ? firstName : "Unknown");
                    newUser.setLastName(lastName != null ? lastName : "Unknown");
                    newUser.setDateOfBirth(Date.valueOf("2000-01-01").toLocalDate());
                    newUser.setRole(Role.USER);

                    return userRepository.save(newUser);
                });

        // Generate JWT Token
        String jwtToken = jwtProvider.generateToken(user);

        // Return JSON response instead of redirect
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "OAuth2 Login Successful!");
        responseBody.put("token", jwtToken);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

}
