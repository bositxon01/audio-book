package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Review;
import uz.pdp.audiobook.payload.ReviewDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "audioBookId", source = "audiobook.id")
    ReviewDTO toDTO(Review review);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    Review toEntity(ReviewDTO reviewDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    void updateReview(ReviewDTO reviewDTO, @MappingTarget Review review);

}
