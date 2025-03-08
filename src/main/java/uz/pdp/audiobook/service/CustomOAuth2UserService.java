package uz.pdp.audiobook.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

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
                    newUser.setUsername(email);
                    newUser.setPassword(PasswordGenerator.generateRandomPassword());
                    newUser.setRole(Role.USER);
                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                    return userRepository.save(newUser);
                });

        String jwtToken = jwtProvider.generateToken(user);

        return new CustomOAuth2User(oAuth2User, jwtToken);
    }
}
