package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.UserProgress;
import uz.pdp.audiobook.payload.UserProgressDTO;
import uz.pdp.audiobook.payload.withoutId.UserProgressDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProgressMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "audiobookId", source = "audiobook.id")
    UserProgressDTO toDTO(UserProgress userProgress);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    UserProgress toEntity(UserProgressDto userProgressDto);

    void updateUserProgressFromDTO(UserProgressDto userProgressDto, @MappingTarget UserProgress userProgress);

}