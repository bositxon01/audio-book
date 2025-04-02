package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.payload.withoutId.AuthorDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDto dto);

    void updateAuthorFromDTO(AuthorDto dto, @MappingTarget Author author);

}
