package uz.pdp.audiobook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.payload.UserPreferencesDTO;
import uz.pdp.audiobook.service.UserPreferencesService;

@RestController
@RequestMapping("/api/user/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferencesService userPreferencesService;

    @GetMapping
    public ResponseEntity<UserPreferencesDTO> getUserPreferences(@AuthenticationPrincipal User user) {
        UserPreferencesDTO dto = userPreferencesService.getUserPreferences(user.getId());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> updateUserPreferences(@AuthenticationPrincipal User user,
                                                   @Valid @RequestBody UserPreferencesDTO preferencesDTO) {
        userPreferencesService.updateUserPreferences(user.getId(), preferencesDTO);
        return ResponseEntity.ok("User preferences updated successfully.");
    }

    @PostMapping("/skip")
    public ResponseEntity<?> skipUserPreferences(@AuthenticationPrincipal User user) {
        userPreferencesService.skipUserPreferences(user.getId());
        return ResponseEntity.ok("User preferences skipped successfully.");
    }

}
