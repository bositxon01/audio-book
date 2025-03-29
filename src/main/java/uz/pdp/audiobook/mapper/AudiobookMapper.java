package uz.pdp.audiobook.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import uz.pdp.audiobook.entity.*;
import uz.pdp.audiobook.payload.AudiobookDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AudiobookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coverImage", expression = "java(mapToAttachment(dto.getCoverImageId(), entityManager))")
    @Mapping(target = "bookAttachment", expression = "java(mapToAttachment(dto.getBookAttachmentId(), entityManager))")
    @Mapping(target = "category", expression = "java(mapToCategory(dto.getCategoryId(), entityManager))")
    Audiobook toEntity(AudiobookDTO dto, @Context EntityManager entityManager);

    @Mapping(target = "coverImageId", source = "coverImage.id")
    @Mapping(target = "bookAttachmentId", source = "bookAttachment.id")
    @Mapping(target = "categoryId", source = "coverImage.id")
    AudiobookDTO toDTO(Audiobook audiobook);

    @Mapping(target = "id", ignore = true)
    void updateAudiobookFromDTO(AudiobookDTO dto, @MappingTarget Audiobook audiobook);

    // Default method to get a managed Attachment reference
    default Attachment mapToAttachment(Integer id, @Context EntityManager entityManager) {
        if (id == null) return null;
        return entityManager.getReference(Attachment.class, id);
    }

    // Default method to get a managed Category reference
    default Category mapToCategory(Integer id, @Context EntityManager entityManager) {
        if (id == null) return null;
        return entityManager.getReference(Category.class, id);
    }

}
