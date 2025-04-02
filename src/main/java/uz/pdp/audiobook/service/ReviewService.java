package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.ReviewDTO;
import uz.pdp.audiobook.payload.withoutId.ReviewDto;

import java.util.List;

public interface ReviewService {

    ApiResult<ReviewDTO> createReview(ReviewDto reviewDto);

    ApiResult<ReviewDTO> getReview(Integer id);

    ApiResult<List<ReviewDTO>> getAllReviews();

    ApiResult<ReviewDTO> updateReview(Integer id, ReviewDto reviewDto);

    ApiResult<Object> deleteReview(Integer id);

    ApiResult<Double> getAverageRating(Integer audioBookId);

}
