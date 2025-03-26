package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Genre;
import uz.pdp.audiobook.payload.GenreDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    // GenreDTO -> Genre (Entity)
    Genre toEntity(GenreDTO dto);

    // Genre (Entity) -> GenreDTO
    GenreDTO toDTO(Genre genre);

    List<GenreDTO> toDTO(List<Genre> genreList);

    // Mavjud Genre obyektini DTO asosida yangilash
    @Mapping(target = "id", ignore = true) // ID o'zgarmasligi uchun
    void updateGenreFromDto(GenreDTO dto, @MappingTarget Genre genre);

}
