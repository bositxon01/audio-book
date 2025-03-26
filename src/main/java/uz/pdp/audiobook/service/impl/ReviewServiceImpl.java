package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.entity.Review;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.mapper.ReviewMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.ReviewDTO;
import uz.pdp.audiobook.repository.AudiobookRepository;
import uz.pdp.audiobook.repository.ReviewRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.ReviewService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final AudiobookRepository audiobookRepository;

    @Override
    @Transactional
    public ApiResult<ReviewDTO> createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);

        Integer userId = reviewDTO.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        review.setUser(user);

        Integer audioBookId = reviewDTO.getAudioBookId();

        Audiobook audiobook = audiobookRepository.findById(audioBookId)
                .orElseThrow(() -> new RuntimeException("AudioBook not found with id: " + audioBookId));

        review.setAudiobook(audiobook);

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
}
