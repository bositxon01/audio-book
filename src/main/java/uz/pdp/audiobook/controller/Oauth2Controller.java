package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.service.Oauth2Service;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Oauth2 API", description = "This API can be used for Oauth2 authorization")
@Hidden
public class Oauth2Controller {

    private final Oauth2Service oauth2Service;

    @GetMapping
    public ResponseEntity<ApiResult<String>> oauth2(Authentication authentication) {
        ApiResult<String> apiResult = oauth2Service.login(authentication);
        return ResponseEntity.ok(apiResult);
    }

}
