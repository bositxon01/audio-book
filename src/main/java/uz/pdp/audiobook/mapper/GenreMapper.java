package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Genre;
import uz.pdp.audiobook.payload.GenreDTO;
import uz.pdp.audiobook.payload.withoutId.GenreDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    GenreDTO toDTO(Genre genre);

    List<GenreDTO> toDTO(List<Genre> genreList);

    Genre toEntity(GenreDto genreDto);

    void updateGenreFromDTO(GenreDto genreDto, @MappingTarget Genre genre);

}
