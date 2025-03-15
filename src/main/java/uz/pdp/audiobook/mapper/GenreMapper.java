package uz.pdp.audiobook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pdp.audiobook.entity.Genre;
import uz.pdp.audiobook.payload.GenreDTO;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    // GenreDTO -> Genre (Entity)
    Genre toEntity(GenreDTO dto);

    // Genre (Entity) -> GenreDTO
    GenreDTO toDto(Genre genre);

    // Mavjud Genre obyektini DTO asosida yangilash
    @Mapping(target = "id", ignore = true) // ID o'zgarmasligi uchun
    void updateGenreFromDto(GenreDTO dto, @MappingTarget Genre genre);
}
