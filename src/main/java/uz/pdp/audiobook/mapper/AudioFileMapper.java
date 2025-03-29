package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.AudioFile;
import uz.pdp.audiobook.payload.AudioFileDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AudioFileMapper {
    AudioFileDTO toDTO(AudioFile audioFile);

    AudioFile toEntity(AudioFileDTO audioFileDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    AudioFile updateAudioFileFromDTO(AudioFileDTO audioFileDTO, @MappingTarget AudioFile audioFile);
}