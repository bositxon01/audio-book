package uz.pdp.audiobook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.security.JWTProvider;
import uz.pdp.audiobook.service.Oauth2Service;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final Oauth2Service oauth2Service;

    @GetMapping
    public ResponseEntity<ApiResult<String>> index(Authentication authentication) {
        ApiResult<String> apiResult = oauth2Service.login(authentication);
        return ResponseEntity.ok(apiResult);
    }
}
