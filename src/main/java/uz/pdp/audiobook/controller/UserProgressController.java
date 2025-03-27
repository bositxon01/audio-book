package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.UserProgressDTO;
import uz.pdp.audiobook.service.UserProgressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/progress")
@Tag(name = "User progress API", description = "CRUD API for tracking user progress in audiobooks")
public class UserProgressController {
    private final UserProgressService userProgressService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<UserProgressDTO>> getUserProgress(@PathVariable Integer id) {
        ApiResult<UserProgressDTO> apiResult = userProgressService.getUserProgress(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<UserProgressDTO>>> getAllUserProgress() {
        ApiResult<List<UserProgressDTO>> result = userProgressService.getAllUserProgress();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ApiResult<UserProgressDTO>> createUserProgress(@Valid @RequestBody UserProgressDTO userProgressDTO) {
        ApiResult<UserProgressDTO> result = userProgressService.createUserProgress(userProgressDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<UserProgressDTO>> updateUserProgress(@PathVariable Integer id, @Valid @RequestBody UserProgressDTO userProgressDTO) {
        ApiResult<UserProgressDTO> result = userProgressService.updateUserProgress(id, userProgressDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteUserProgress(@PathVariable Integer id) {
        ApiResult<Object> result = userProgressService.deleteUserProgress(id);
        return ResponseEntity.ok(result);
    }

}
