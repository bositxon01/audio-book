package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.LoginDTO;
import uz.pdp.audiobook.payload.RegisterDTO;
import uz.pdp.audiobook.service.AuthService;
import uz.pdp.audiobook.service.CustomOAuth2User;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Authentication CRUD API")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/oauth-success")
    public Map<String, Object> oauthSuccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println("DEBUG Principal Class: " + principal.getClass().getName());

        if (principal instanceof CustomOAuth2User customUser) {
            return Map.of(
                    "message", "OAuth login successful!",
                    "jwtToken", customUser.getJwtToken()
            );
        }

        return Map.of("success", false, "message", "OAuth user not found in context");
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResult<String>> register(@Valid @RequestBody RegisterDTO registerDTO) {
        ApiResult<String> register = authService.register(registerDTO);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResult<String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        ApiResult<String> login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResult<String>> confirm(@RequestParam String email,
                                                     @RequestParam String code) {
        ApiResult<String> confirm = authService.confirm(email, code);
        return ResponseEntity.ok(confirm);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResult<String>> forgetPassword(@RequestParam String email) {
        ApiResult<String> forgetPassword = authService.forgetPassword(email);
        return ResponseEntity.ok(forgetPassword);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResult<String>> resetPassword(@RequestParam String email,
                                                           @RequestParam String token,
                                                           @RequestParam String newPassword) {
        ApiResult<String> resetPassword = authService.resetPassword(email, token, newPassword);
        return ResponseEntity.ok(resetPassword);
    }

}
