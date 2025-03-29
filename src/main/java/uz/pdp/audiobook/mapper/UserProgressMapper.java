package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.UserProgress;
import uz.pdp.audiobook.payload.UserProgressDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProgressMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "audiobookId", source = "audiobook.id")
    UserProgressDTO toDTO(UserProgress userProgress);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobook", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserProgress toEntity(UserProgressDTO userProgressDTO);

    @Mapping(target = "id", ignore = true)
    void updateUserProgressFromDTO(UserProgressDTO userProgressDTO, @MappingTarget UserProgress userProgress);

}