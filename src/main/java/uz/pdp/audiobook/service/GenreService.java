package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.GenreDTO;

import java.util.List;

public interface GenreService {

    ApiResult<GenreDTO> createGenre(GenreDTO genreDTO);

    ApiResult<GenreDTO> getGenre(Integer id);

    ApiResult<List<GenreDTO>> getAllGenre();

    ApiResult<GenreDTO> updateGenre(Integer id, GenreDTO genreDTO);

    ApiResult<Object> deleteGenre(Integer id);

}
