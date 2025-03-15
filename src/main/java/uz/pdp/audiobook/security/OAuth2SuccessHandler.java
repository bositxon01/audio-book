package uz.pdp.audiobook.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.repository.UserRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    public OAuth2SuccessHandler(UserRepository userRepository, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByUsernameAndDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        String jwtToken = jwtProvider.generateToken(user);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/json");

        response.getWriter().write("{\"jwt\":\"" + jwtToken + "\"}");

        /*
        Redirect to frontend with token
        response.sendRedirect("http://localhost:3000/oauth-success?token=" + jwtToken);
         */
    }

}
