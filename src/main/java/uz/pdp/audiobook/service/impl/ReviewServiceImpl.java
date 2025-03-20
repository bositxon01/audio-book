package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Review;
import uz.pdp.audiobook.mapper.ReviewMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.ReviewDTO;
import uz.pdp.audiobook.repository.ReviewRepository;
import uz.pdp.audiobook.service.ReviewService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ApiResult<ReviewDTO> createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);
        reviewRepository.save(review);
        return ApiResult.success(reviewMapper.toDto(review));
    }

    @Override
    public ApiResult<ReviewDTO> getReview(Integer id) {
        return reviewRepository.findByIdAndDeletedFalse(id)
                .map(reviewMapper::toDto)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Review not found with id " + id));
    }

    @Override
    public ApiResult<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewRepository.findByDeletedFalse().stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ApiResult.success(reviews);
    }

    @Override
    public ApiResult<ReviewDTO> updateReview(Integer id, ReviewDTO reviewDTO) {
        return reviewRepository.findByIdAndDeletedFalse(id)
                .map(existingReview -> {
                    reviewMapper.updateReview(reviewDTO, existingReview);
                    reviewRepository.save(existingReview);
                    return ApiResult.success(reviewMapper.toDto(existingReview));
                }).orElse(ApiResult.error("Review not found with id: " + id));
    }

    @Override
    public ApiResult<Object> deleteReview(Integer id) {
        Optional<Review> optionalReview = reviewRepository.findByIdAndDeletedFalse(id);

        if (optionalReview.isEmpty()) {
            return ApiResult.error("Review not found with id: " + id);
        }

        Review review = optionalReview.get();
        review.setDeleted(true);
        reviewRepository.save(review);

        return ApiResult.success("Review deleted successfully.");
    }
}
