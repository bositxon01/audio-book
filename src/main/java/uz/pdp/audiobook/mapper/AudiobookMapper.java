package uz.pdp.audiobook.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import uz.pdp.audiobook.entity.Attachment;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.payload.withoutId.AudiobookDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AudiobookMapper {

    @Mapping(target = "coverImage", expression = "java(mapToAttachment(audiobookDto.getCoverImageId(), entityManager))")
    @Mapping(target = "bookAttachment", expression = "java(mapToAttachment(audiobookDto.getBookAttachmentId(), entityManager))")
    @Mapping(target = "category", expression = "java(mapToCategory(audiobookDto.getCategoryId(), entityManager))")
    Audiobook toEntity(AudiobookDto audiobookDto, @Context EntityManager entityManager);

    @Mapping(target = "coverImageId", source = "coverImage.id")
    @Mapping(target = "bookAttachmentId", source = "bookAttachment.id")
    @Mapping(target = "categoryId", source = "coverImage.id")
    AudiobookDTO toDTO(Audiobook audiobook);

    void updateAudiobookFromDTO(AudiobookDto audiobookDto, @MappingTarget Audiobook audiobook);

    default Attachment mapToAttachment(Integer id, @Context EntityManager entityManager) {
        if (id == null) return null;
        return entityManager.getReference(Attachment.class, id);
    }

    default Category mapToCategory(Integer id, @Context EntityManager entityManager) {
        if (id == null) return null;
        return entityManager.getReference(Category.class, id);
    }

}
