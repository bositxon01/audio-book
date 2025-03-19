package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.ReviewDTO;

import java.util.List;

public interface ReviewService {


    ApiResult<ReviewDTO> createReview(ReviewDTO reviewDTO);

    ApiResult<ReviewDTO> getReview(Integer id);

    ApiResult<List<ReviewDTO>> getAllReviews();

    ApiResult<ReviewDTO> updateReview(Integer id, ReviewDTO reviewDTO);

    ApiResult<Object> deleteReview(Integer id);
}
