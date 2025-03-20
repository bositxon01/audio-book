package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Genre;
import uz.pdp.audiobook.payload.GenreDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    // GenreDTO -> Genre (Entity)
    Genre toEntity(GenreDTO dto);

    // Genre (Entity) -> GenreDTO
    GenreDTO toDTO(Genre genre);

    // Mavjud Genre obyektini DTO asosida yangilash
    @Mapping(target = "id", ignore = true) // ID o'zgarmasligi uchun
    void updateGenreFromDto(GenreDTO dto, @MappingTarget Genre genre);

}
