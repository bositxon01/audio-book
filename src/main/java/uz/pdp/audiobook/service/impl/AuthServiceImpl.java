package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.enums.Role;
import uz.pdp.audiobook.mapper.UserMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.LoginDTO;
import uz.pdp.audiobook.payload.RegisterDTO;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.security.JWTProvider;
import uz.pdp.audiobook.service.AuthService;
import uz.pdp.audiobook.service.EmailService;
import uz.pdp.audiobook.utils.VerificationInfo;

import java.util.Map;
import java.util.Objects;

import static uz.pdp.audiobook.utils.VerificationCodeGenerator.generateVerificationCode;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, VerificationInfo> verificationData;

    private static final Integer EXPIRY_TIME = 120_000;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public ApiResult<String> register(RegisterDTO registerDTO) {

        String username = registerDTO.getUsername();

        if (userRepository.existsByUsernameAndDeletedFalse(username))
            return ApiResult.error("Username already in use");

        User user = userMapper.toEntity(registerDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        String verificationCode = generateVerificationCode();

        verificationData.put(username,
                VerificationInfo.builder()
                        .user(user)
                        .code(verificationCode)
                        .expiryTime(System.currentTimeMillis() + EXPIRY_TIME)
                        .attempts(0)
                        .build()
        );

        emailService.sendEmail(
                username,
                verificationCode,
                "Email Verification",
                "Your verification code is: " + verificationCode);

        return ApiResult.success("Verification code sent to " + username);
    }

    @Override
    public ApiResult<String> confirm(String email, String code) {

        VerificationInfo verificationInfo = verificationData.get(email);

        if (verificationInfo == null || System.currentTimeMillis() > verificationInfo.getExpiryTime()) {
            verificationData.remove(email);
            return ApiResult.error("The verification code has expired. Please request a new one.");
        }

        if (!code.equals(verificationInfo.getCode())) {
            if (verificationInfo.getAttempts() >= 3) {
                verificationData.remove(email);
                return ApiResult.error("Too many incorrect attempts. Please request a new verification code.");
            }
            verificationInfo.setAttempts(verificationInfo.getAttempts() + 1);
            return ApiResult.error("Incorrect verification code. Attempts left: " + (3 - verificationInfo.getAttempts()));
        }

        User user = verificationInfo.getUser();

        userRepository.save(user);

        verificationData.remove(email);

        return ApiResult.success("Verification successful. You can log in.");
    }

    @Override
    public ApiResult<String> login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtProvider.generateToken(user);

        System.out.println("token: " + token);

        return ApiResult.success(token);
    }

    @Override
    public ApiResult<String> forgetPassword(String username) {

        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String resetToken = jwtProvider.generateToken(user);

        verificationData.put(username,
                VerificationInfo.builder()
                        .user(user)
                        .code(resetToken)
                        .expiryTime(System.currentTimeMillis() + EXPIRY_TIME)
                        .attempts(0)
                        .build()
        );

        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + resetToken + "&username=" + username;

        emailService.sendEmail(
                username,
                resetLink,
                "Reset password link",
                "Click the link to reset your password: " + resetLink);


        return ApiResult.success("Password reset link sent to " + username);
    }

    @Override
    public ApiResult<String> resetPassword(String email, String token, String newPassword) {

        VerificationInfo verificationInfo = verificationData.get(email);

        if ((verificationInfo == null) ||
                (System.currentTimeMillis() > verificationInfo.getExpiryTime())) {
            verificationData.remove(email);
            return ApiResult.error("Invalid or expired password reset token.");
        }

        String extractedEmail;
        try {
            extractedEmail = jwtProvider.validateToken(token);
        } catch (Exception e) {
            return ApiResult.error("Invalid password reset token.");
        }

        if (!extractedEmail.equals(email)) {
            return ApiResult.error("Token does not match the provided email.");
        }

        User user = verificationInfo.getUser();

        if (Objects.isNull(user)) {
            return ApiResult.error("User not found with username: " + email);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        verificationData.remove(email);

        return ApiResult.success("Password reset successful. You can now log in with your new password.");
    }

}
