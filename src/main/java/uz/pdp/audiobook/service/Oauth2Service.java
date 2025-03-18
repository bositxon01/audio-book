package uz.pdp.audiobook.service;

import org.springframework.security.core.Authentication;
import uz.pdp.audiobook.payload.ApiResult;

public interface Oauth2Service {

    ApiResult<String> login(Authentication authentication);

}
