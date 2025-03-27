package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.mapper.AuthorMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.repository.AuthorRepository;
import uz.pdp.audiobook.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public ApiResult<AuthorDTO> createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorRepository.save(author);
        return ApiResult.success(authorMapper.toDTO(author));
    }

    @Override
    public ApiResult<AuthorDTO> getAuthor(Integer id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Author not found with id " + id));
    }

    @Override
    //  @PreAuthorize("hasRole(T(uz.pdp.audiobook.enums.Role).SUPER_ADMIN)")
    public ApiResult<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();

        return ApiResult.success(authors);
    }

    @Override
    @Transactional
    public ApiResult<AuthorDTO> updateAuthor(Integer id, AuthorDTO authorDTO) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    authorMapper.updateAuthorFromDto(authorDTO, existingAuthor);
                    authorRepository.save(existingAuthor);
                    return ApiResult.success(authorMapper.toDTO(existingAuthor));
                })
                .orElse(ApiResult.error("Author not found with id: " + id));
    }

    @Override
    @Transactional
    public ApiResult<Object> deleteAuthor(Integer id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isEmpty()) {
            return ApiResult.error("Author not found with id: " + id);
        }

        Author author = optionalAuthor.get();
        author.setDeleted(true);

        authorRepository.save(author);
        return ApiResult.success("Author deleted successfully.");
    }

}
