package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.UserPreferencesDTO;
import uz.pdp.audiobook.payload.UserPreferencesGetDTO;
import uz.pdp.audiobook.service.UserPreferencesService;

@RestController
@RequestMapping("/api/user/preferences")
@RequiredArgsConstructor
@Tag(name = "User preferences API", description = "API for getting, setting user preferences")
public class UserPreferenceController {

    private final UserPreferencesService userPreferencesService;

    @GetMapping
    public ResponseEntity<ApiResult<UserPreferencesGetDTO>> getUserPreferences(@AuthenticationPrincipal User user) {
        UserPreferencesGetDTO dto = userPreferencesService.getUserPreferences(user.getId());
        return ResponseEntity.ok(ApiResult.success(dto));
    }

    @PutMapping
    public ResponseEntity<ApiResult<UserPreferencesDTO>> updateUserPreferences(@AuthenticationPrincipal User user,
                                                   @Valid @RequestBody UserPreferencesDTO preferencesDTO) {
        userPreferencesService.updateUserPreferences(user.getId(), preferencesDTO);
        return ResponseEntity.ok(ApiResult.success("User preferences updated successfully"));
    }

    @PostMapping("/skip")
    public ResponseEntity<ApiResult<UserPreferencesDTO>> skipUserPreferences(@AuthenticationPrincipal User user) {
        userPreferencesService.skipUserPreferences(user.getId());
        return ResponseEntity.ok(ApiResult.success("User preferences was skipped"));
    }

}
