package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.payload.UserPreferencesByIdsDTO;
import uz.pdp.audiobook.payload.UserPreferencesByNamesDTO;
import uz.pdp.audiobook.payload.UserPreferencesGetDTO;
import uz.pdp.audiobook.service.UserPreferencesService;

@RestController
@RequestMapping("/api/user/preferences")
@RequiredArgsConstructor
@Tag(name = "User Preferences API", description = "API for getting and setting user preferences")
public class UserPreferenceController {

    private final UserPreferencesService userPreferencesService;

    @GetMapping
    public ResponseEntity<UserPreferencesGetDTO> getUserPreferences(@AuthenticationPrincipal User user) {
        UserPreferencesGetDTO dto = userPreferencesService.getUserPreferences(user.getId());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/ids")
    public ResponseEntity<String> updateUserPreferencesByIds(@AuthenticationPrincipal User user,
                                                             @Valid @RequestBody UserPreferencesByIdsDTO preferencesDTO) {
        userPreferencesService.updateUserPreferencesByIds(user.getId(), preferencesDTO);
        return ResponseEntity.ok("User preferences updated successfully (by IDs).");
    }

    @PutMapping("/names")
    public ResponseEntity<String> updateUserPreferencesByNames(@AuthenticationPrincipal User user,
                                                               @Valid @RequestBody UserPreferencesByNamesDTO preferencesDTO) {
        userPreferencesService.updateUserPreferencesByNames(user.getId(), preferencesDTO);
        return ResponseEntity.ok("User preferences updated successfully (by Names).");
    }

    @PostMapping("/skip")
    public ResponseEntity<String> skipUserPreferences(@AuthenticationPrincipal User user) {
        userPreferencesService.skipUserPreferences(user.getId());
        return ResponseEntity.ok("User preferences skipped successfully.");
    }
}
