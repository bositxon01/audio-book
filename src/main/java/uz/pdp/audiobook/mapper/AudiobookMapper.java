package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.entity.AudiobookDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING, uses = {AttachmentMapper.class, AttachmentMapper.class})
public interface AudiobookMapper {
    Audiobook toEntity(AudiobookDTO audiobookDTO);

    AudiobookDTO toDTO(Audiobook audiobook);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Audiobook partialUpdate(AudiobookDTO audiobookDTO, @MappingTarget Audiobook audiobook);
}