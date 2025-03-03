package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.LoginDTO;
import uz.pdp.audiobook.payload.RegisterDTO;
import uz.pdp.audiobook.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Authentication CRUD API")
public class AuthController {

    private final AuthService authService;

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

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResult<String>> forgetPassword(@RequestParam String email) {
        ApiResult<String> forgetPassword = authService.forgetPassword(email);
        return ResponseEntity.ok(forgetPassword);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResult<String>> resetPassword(@RequestParam String email,
                                                           @RequestParam String code,
                                                           @RequestParam String newPassword) {
        ApiResult<String> resetPassword = authService.resetPassword(email, code, newPassword);
        return ResponseEntity.ok(resetPassword);
    }


}
