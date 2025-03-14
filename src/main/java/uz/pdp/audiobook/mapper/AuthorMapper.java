package uz.pdp.audiobook.mapper;

import org.mapstruct.Mapper;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.payload.AuthorDTO;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorDTO dto);
    AuthorDTO toDto(Author author);
}
