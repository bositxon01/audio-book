package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Review;
import uz.pdp.audiobook.payload.ReviewDTO;
import uz.pdp.audiobook.payload.withoutId.ReviewDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "audioBookId", source = "audiobook.id")
    ReviewDTO toDTO(Review review);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    Review toEntity(ReviewDto reviewDto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    void updateReviewFromDTO(ReviewDto reviewDto, @MappingTarget Review review);

}
