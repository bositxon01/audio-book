package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.GenreDTO;
import uz.pdp.audiobook.payload.withoutId.GenreDto;

import java.util.List;

public interface GenreService {

    ApiResult<GenreDTO> createGenre(GenreDto genreDto);

    ApiResult<List<GenreDTO>> createGenres(List<GenreDto> genreDtoList);

    ApiResult<GenreDTO> getGenre(Integer id);

    ApiResult<List<GenreDTO>> getAllGenre();

    ApiResult<GenreDTO> updateGenre(Integer id, GenreDto genreDto);

    ApiResult<Object> deleteGenre(Integer id);

}
