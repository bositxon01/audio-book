package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public ApiResult<ReviewDTO> createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);
        reviewRepository.save(review);
        return ApiResult.success(reviewMapper.toDTO(review));
    }

    @Override
    public ApiResult<ReviewDTO> getReview(Integer id) {
        return reviewRepository.findByIdAndDeletedFalse(id)
                .map(reviewMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Review not found with id " + id));
    }

    @Override
    public ApiResult<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewRepository.findByDeletedFalse()
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
        return ApiResult.success(reviews);
    }

    @Override
    @Transactional
    public ApiResult<ReviewDTO> updateReview(Integer id, ReviewDTO reviewDTO) {
        return reviewRepository.findByIdAndDeletedFalse(id)
                .map(existingReview -> {
                    reviewMapper.updateReview(reviewDTO, existingReview);
                    reviewRepository.save(existingReview);
                    return ApiResult.success(reviewMapper.toDTO(existingReview));
                })
                .orElse(ApiResult.error("Review not found with id: " + id));
    }

    @Override
    @Transactional
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

    @Override
    public ApiResult<Double> getAverageRating(Integer audioBookId) {
        List<Review> reviews = reviewRepository.findAllByAudiobookIdAndDeletedFalse(audioBookId);

        if (reviews.isEmpty()) {
            return ApiResult.error("No reviews found for this audio book");
        }

        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        return ApiResult.success(averageRating);
    }

}
