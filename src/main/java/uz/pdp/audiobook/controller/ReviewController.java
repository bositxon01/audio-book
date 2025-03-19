package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.ReviewDTO;
import uz.pdp.audiobook.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Review CRUD API")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResult<ReviewDTO>> addReview(@RequestBody ReviewDTO reviewDTO) {
        ApiResult<ReviewDTO> result = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<ReviewDTO>> getReview(@PathVariable Integer id) {
        ApiResult<ReviewDTO> result = reviewService.getReview(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<ReviewDTO>>> getAllReviews() {
        ApiResult<List<ReviewDTO>> result = reviewService.getAllReviews();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<ReviewDTO>> updateReview(@PathVariable Integer id,
                                                             @RequestBody ReviewDTO reviewDTO) {
        ApiResult<ReviewDTO> result = reviewService.updateReview(id, reviewDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteReview(@PathVariable Integer id) {
        ApiResult<Object> result = reviewService.deleteReview(id);
        return ResponseEntity.ok(result);
    }


}
