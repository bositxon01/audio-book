package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.ReviewDTO;
import uz.pdp.audiobook.payload.withoutId.ReviewDto;
import uz.pdp.audiobook.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Review CRUD API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResult<ReviewDTO>> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        ApiResult<ReviewDTO> apiResult = reviewService.createReview(reviewDto);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<ReviewDTO>> getReview(@PathVariable Integer id) {
        ApiResult<ReviewDTO> apiResult = reviewService.getReview(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<ReviewDTO>>> getAllReviews() {
        ApiResult<List<ReviewDTO>> apiResult = reviewService.getAllReviews();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/average-rating")
    public ResponseEntity<ApiResult<Double>> getAverageRating(@RequestParam Integer id) {
        ApiResult<Double> apiResult = reviewService.getAverageRating(id);
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<ReviewDTO>> updateReview(@PathVariable Integer id,
                                                             @Valid @RequestBody ReviewDto reviewDto) {
        ApiResult<ReviewDTO> apiResult = reviewService.updateReview(id, reviewDto);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteReview(@PathVariable Integer id) {
        ApiResult<Object> apiResult = reviewService.deleteReview(id);
        return ResponseEntity.ok(apiResult);
    }

}
