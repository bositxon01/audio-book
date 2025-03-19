package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Attachment;
import uz.pdp.audiobook.payload.AttachmentDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {

    Attachment toEntity(AttachmentDTO attachmentDTO);

    @Mapping(target = "filename", ignore = true)
    AttachmentDTO toDTO(Attachment attachment);

    List<AttachmentDTO> toDTO(List<Attachment> attachments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attachment partialUpdate(AttachmentDTO attachmentDTO, @MappingTarget Attachment attachment);
}