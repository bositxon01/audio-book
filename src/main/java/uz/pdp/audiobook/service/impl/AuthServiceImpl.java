package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.User;
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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserMapper userMapper;
    private final EmailService emailService;

    private final Map<String, VerificationInfo> verificationData = new ConcurrentHashMap<>();
    private static final Integer EXPIRY_TIME = 60_000;
    private static final Integer CLEAN_TIME = 60_000;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public ApiResult<String> register(RegisterDTO registerDTO) {

        if (userRepository.existsByUsernameAndDeletedFalse(registerDTO.getUsername()))
            return ApiResult.error("Username already in use");

        User user = userMapper.toEntity(registerDTO);

        String verificationCode = generateVerificationCode();

        verificationData.put(registerDTO.getUsername(),
                VerificationInfo.builder()
                        .user(user)
                        .code(verificationCode)
                        .expiryTime(System.currentTimeMillis() + EXPIRY_TIME)
                        .attempts(0)
                        .build()
        );

        emailService.sendVerificationEmail(registerDTO.getUsername(), verificationCode);

        return ApiResult.success("Verification code sent to " + registerDTO.getUsername());
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

        return ApiResult.success("Token generated for user: ", token);
    }

    @Override
    public ApiResult<String> forgetPassword(String email) {
        return null;
    }

    @Override
    public ApiResult<String> resetPassword(String email, String code, String newPassword) {
        return null;
    }

    @Scheduled(fixedRate = 60_000)
    public void cleanupExpiredTokens() {
        long now = System.currentTimeMillis();

        verificationData.entrySet()
                .removeIf(
                        entry ->
                                now > entry.getValue().getExpiryTime());
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(100000, 999999));
    }
}
