package uz.pdp.audiobook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pdp.audiobook.entity.Review;
import uz.pdp.audiobook.payload.ReviewDTO;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    Review toEntity(ReviewDTO reviewDTO);

    ReviewDTO toDto(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    void updateReview(ReviewDTO reviewDTO, @MappingTarget Review review);
}
