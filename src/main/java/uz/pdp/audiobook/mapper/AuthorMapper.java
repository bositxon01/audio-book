package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.payload.AuthorDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author author);

    @Mapping(target = "id", ignore = true) // ID o'zgarmasligi uchun
    void updateAuthorFromDto(AuthorDTO dto, @MappingTarget Author author);

}
