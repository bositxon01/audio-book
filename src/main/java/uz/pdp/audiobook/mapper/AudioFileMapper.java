package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.AudioFile;
import uz.pdp.audiobook.payload.AudioFileDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AudioFileMapper {
    AudioFile toEntity(AudioFileDTO audioFileDTO);

    AudioFileDTO toDTO(AudioFile audioFile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AudioFile partialUpdate(AudioFileDTO audioFileDTO, @MappingTarget AudioFile audioFile);
}