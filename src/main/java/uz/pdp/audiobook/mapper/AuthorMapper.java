package uz.pdp.audiobook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.payload.AuthorDTO;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorDTO dto);
    AuthorDTO toDto(Author author);

    @Mapping(target = "id", ignore = true) // ID o'zgarmasligi uchun
    void updateAuthorFromDto(AuthorDTO dto, @MappingTarget Author author);

}
