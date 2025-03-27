package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.payload.AuthorDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorDTO toDTO(Author author);

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateAuthorFromDTO(AuthorDTO dto, @MappingTarget Author author);

}
