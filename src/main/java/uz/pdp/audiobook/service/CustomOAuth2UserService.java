package uz.pdp.audiobook.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.enums.Role;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.security.JWTProvider;
import uz.pdp.audiobook.utils.PasswordGenerator;

import java.sql.Date;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(UserRepository userRepository, JWTProvider jwtProvider, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found in OAuth provider response");
        }

        User user = userRepository.findByUsernameAndDeletedFalse(email)
                .orElseGet(() -> {
                    User newUser = new User();

                    String password = PasswordGenerator.generateRandomPassword();
                    newUser.setUsername(email);
                    newUser.setPassword(passwordEncoder.encode(password));
                    newUser.setRole(Role.USER);
                    newUser.setFirstName(firstName != null ? firstName : "Unknown");
                    newUser.setLastName(lastName != null ? lastName : "Unknown");
                    newUser.setDateOfBirth(Date.valueOf("2000-01-01"));
                    return userRepository.save(newUser);
                });

        String jwtToken = jwtProvider.generateToken(user);

        return new CustomOAuth2User(oAuth2User, jwtToken);
    }
}
