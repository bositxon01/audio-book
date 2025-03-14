package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;

import java.util.List;

public interface AuthorService {

    ApiResult<AuthorDTO> createAuthor(AuthorDTO authorDTO);

    ApiResult<AuthorDTO> getAuthor(Long id);

    ApiResult<List<AuthorDTO>> getAllAuthors();

    ApiResult<AuthorDTO> updateAuthor(Long id, AuthorDTO authorDTO);

    ApiResult<Object> deleteAuthor(Long id);
}
