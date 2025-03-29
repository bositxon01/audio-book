package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Attachment;
import uz.pdp.audiobook.payload.AttachmentDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {

    @Mapping(target = "filename", ignore = true)
    AttachmentDTO toDTO(Attachment attachment);

    List<AttachmentDTO> toDTO(List<Attachment> attachments);

    Attachment toEntity(AttachmentDTO attachmentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attachment updateAttachmentFromDTO(AttachmentDTO attachmentDTO, @MappingTarget Attachment attachment);

}