package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.LoginDTO;
import uz.pdp.audiobook.payload.RegisterDTO;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public ApiResult<String> register(RegisterDTO registerDTO) {
        return null;
    }

    @Override
    public ApiResult<String> login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public ApiResult<String> forgetPassword(String email) {
        return null;
    }

    @Override
    public ApiResult<String> resetPassword(String email, String code, String newPassword) {
        return null;
    }
}
