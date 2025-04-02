package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.security.JWTProvider;
import uz.pdp.audiobook.service.Oauth2Service;

@Service
@RequiredArgsConstructor
public class Oauth2ServiceImpl implements Oauth2Service {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    @Override
    public ApiResult<String> login(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByUsernameAndDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        String jwtToken = jwtProvider.generateToken(user);

        return ApiResult.success("Successfully logged in through Oauth2!", jwtToken);
    }

}
