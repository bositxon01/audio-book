package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;

import java.util.List;

public interface AuthorService {

    ApiResult<AuthorDTO> createAuthor(AuthorDTO authorDTO);

    ApiResult<AuthorDTO> getAuthor(Integer id);

    ApiResult<List<AuthorDTO>> getAllAuthors();

    ApiResult<AuthorDTO> updateAuthor(Integer id, AuthorDTO authorDTO);

    ApiResult<Object> deleteAuthor(Integer id);
}
