package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.enums.Role;
import uz.pdp.audiobook.mapper.AuthorMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.repository.AuthorRepository;
import uz.pdp.audiobook.service.AuthorService;

import java.util.List;
import java.util.Optional;
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
    public ApiResult<AuthorDTO> getAuthor(Integer id) {
        return authorRepository.findByIdAndDeletedFalse(id)
                .map(authorMapper::toDto)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Author not found with id " + id));
    }

    @Override
  //  @PreAuthorize("hasRole(T(uz.pdp.audiobook.enums.Role).SUPER_ADMIN)")
    public ApiResult<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorRepository.findByDeletedFalse().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        return ApiResult.success(authors);
    }

    @Override
    public ApiResult<AuthorDTO> updateAuthor(Integer id, AuthorDTO authorDTO) {
        return authorRepository.findByIdAndDeletedFalse(id)
                .map(existingAuthor -> {
                    authorMapper.updateAuthorFromDto(authorDTO, existingAuthor);
                    authorRepository.save(existingAuthor);
                    return ApiResult.success(authorMapper.toDto(existingAuthor));
                }).orElse(ApiResult.error("Author not found with id: " + id));
    }


    @Override
    public ApiResult<Object> deleteAuthor(Integer id) {
        Optional<Author> optionalAuthor = authorRepository.findByIdAndDeletedFalse(id);

        if (optionalAuthor.isEmpty()) {
            return ApiResult.error("Author not found with id: " + id);
        }

        Author author = optionalAuthor.get();
        author.setDeleted(true);

        authorRepository.save(author);
        return ApiResult.success("Author deleted successfully.");
    }
}
