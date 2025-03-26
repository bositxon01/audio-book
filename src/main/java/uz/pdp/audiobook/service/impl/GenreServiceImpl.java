package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Genre;
import uz.pdp.audiobook.mapper.GenreMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.GenreDTO;
import uz.pdp.audiobook.repository.GenreRepository;
import uz.pdp.audiobook.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public ApiResult<GenreDTO> createGenre(GenreDTO genreDTO) {
        Genre genre = genreMapper.toEntity(genreDTO);
        genreRepository.save(genre);
        return ApiResult.success(genreMapper.toDTO(genre));
    }

    @Override
    public ApiResult<List<GenreDTO>> createGenres(List<GenreDTO> genreDTOList) {
        List<Genre> genres = new ArrayList<>();

        for (GenreDTO genreDTO : genreDTOList) {
            Genre genre = genreMapper.toEntity(genreDTO);
            genreRepository.save(genre);
            genres.add(genre);
        }

        return ApiResult.success(genreMapper.toDTO(genres));
    }

    @Override
    public ApiResult<GenreDTO> getGenre(Integer id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Genre not found with id " + id));
    }

    @Override
    public ApiResult<List<GenreDTO>> getAllGenre() {
        List<GenreDTO> genres = genreRepository.findAll()
                .stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());
        return ApiResult.success(genres);
    }

    @Override
    @Transactional
    public ApiResult<GenreDTO> updateGenre(Integer id, GenreDTO genreDTO) {
        return genreRepository.findById(id)
                .map(existingGenre -> {
                    genreMapper.updateGenreFromDto(genreDTO, existingGenre);
                    genreRepository.save(existingGenre);
                    return ApiResult.success(genreMapper.toDTO(existingGenre));
                })
                .orElse(ApiResult.error("Genre not found with id: " + id));
    }

    @Override
    @Transactional
    public ApiResult<Object> deleteGenre(Integer id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if (optionalGenre.isEmpty()) {
            return ApiResult.error("Genre not found with id: " + id);
        }

        Genre genre = optionalGenre.get();
        genre.setDeleted(true);
        genreRepository.save(genre);

        return ApiResult.success("Genre deleted successfully.");
    }

}
