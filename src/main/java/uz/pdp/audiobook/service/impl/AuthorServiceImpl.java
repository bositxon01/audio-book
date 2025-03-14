package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.mapper.AuthorMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.repository.AuthorRepository;
import uz.pdp.audiobook.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public ApiResult<AuthorDTO> createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorRepository.save(author);
        return ApiResult.success(authorMapper.toDto(author));
    }

    @Override
    public ApiResult<AuthorDTO> getAuthor(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Author not found."));
    }

    @Override
    public ApiResult<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        return ApiResult.success(authors);
    }

    @Override
    public ApiResult<AuthorDTO> updateAuthor(Long id, AuthorDTO authorDTO) {
        return authorRepository.findById(id).map(existingAuthor -> {
            existingAuthor.setFirstName(authorDTO.getFirstName());
            existingAuthor.setLastName(authorDTO.getLastName());
            existingAuthor.setBiography(authorDTO.getBiography());
            existingAuthor.setDateOfBirth(authorDTO.getDateOfBirth());
            authorRepository.save(existingAuthor);
            return ApiResult.success(authorMapper.toDto(existingAuthor));
        }).orElse(ApiResult.error("Author not found."));
    }

    @Override
    public ApiResult<Object> deleteAuthor(Long id) {
        return authorRepository.findById(id).map(author -> {
            authorRepository.delete(author);
            return ApiResult.success("Author deleted successfully.");
        }).orElse(ApiResult.error("Author not found."));
    }
}
