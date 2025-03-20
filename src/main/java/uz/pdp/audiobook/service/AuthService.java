package uz.pdp.audiobook.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.LoginDTO;
import uz.pdp.audiobook.payload.RegisterDTO;

public interface AuthService extends UserDetailsService {

    ApiResult<String> register(RegisterDTO registerDTO);

    ApiResult<String> confirm(String email, String code);

    ApiResult<String> login(LoginDTO loginDTO);

    ApiResult<String> forgetPassword(String email);

    ApiResult<String> resetPassword(String email, String token, String newPassword);

}
