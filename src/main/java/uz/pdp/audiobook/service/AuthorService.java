package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.payload.withoutId.AuthorDto;

import java.util.List;

public interface AuthorService {

    ApiResult<AuthorDTO> createAuthor(AuthorDto authorDTO);

    ApiResult<AuthorDTO> getAuthor(Integer id);

    ApiResult<List<AuthorDTO>> getAllAuthors();

    ApiResult<AuthorDTO> updateAuthor(Integer id, AuthorDto authorDTO);

    ApiResult<Object> deleteAuthor(Integer id);

}
