package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Genre;
import uz.pdp.audiobook.payload.GenreDTO;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    GenreDTO toDTO(Genre genre);

    List<GenreDTO> toDTO(List<Genre> genreList);

    @Mapping(target = "id", ignore = true)
    Genre toEntity(GenreDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateGenreFromDTO(GenreDTO dto, @MappingTarget Genre genre);

}
