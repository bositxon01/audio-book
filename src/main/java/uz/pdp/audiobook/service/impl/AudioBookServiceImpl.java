package uz.pdp.audiobook.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.*;
import uz.pdp.audiobook.mapper.AudioFileMapper;
import uz.pdp.audiobook.mapper.AudiobookMapper;
import uz.pdp.audiobook.mapper.AuthorMapper;
import uz.pdp.audiobook.mapper.GenreMapper;
import uz.pdp.audiobook.payload.*;
import uz.pdp.audiobook.repository.AudioBookAuthorsRepository;
import uz.pdp.audiobook.repository.AudioBookGenresRepository;
import uz.pdp.audiobook.repository.AudiobookRepository;
import uz.pdp.audiobook.service.AudioBookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AudioBookServiceImpl implements AudioBookService {

    private final AudiobookRepository audiobookRepository;
    private final AudiobookMapper audiobookMapper;
    private final AudioFileMapper audioFileMapper;
    private final AudioBookAuthorsRepository audioBookAuthorsRepository;
    private final AuthorMapper authorMapper;
    private final AudioBookGenresRepository audioBookGenresRepository;
    private final GenreMapper genreMapper;
    private final EntityManager entityManager;

    @Override
    public ApiResult<AudiobookDTO> getAudioBookById(Integer id) {
        Optional<Audiobook> optionalAudiobook = audiobookRepository.findById(id);
        if (optionalAudiobook.isEmpty())
            return ApiResult.error("Audiobook not found with id: " + id);

        Audiobook audiobook = optionalAudiobook.get();
        AudiobookDTO audiobookDTO = audiobookMapper.toDTO(audiobook);
        populateAssociations(audiobook, audiobookDTO);

        return ApiResult.success(audiobookDTO);
    }

    @Override
    public ApiResult<List<AudiobookDTO>> getAudioBooks() {
        List<AudiobookDTO> audiobooks = audiobookRepository.findAll()
                .stream()
                .map(audiobook -> {
                    AudiobookDTO audiobookDTO = audiobookMapper.toDTO(audiobook);
                    populateAssociations(audiobook, audiobookDTO);
                    return audiobookDTO;
                })
                .toList();

        return ApiResult.success(audiobooks);
    }

    @Override
    @Transactional
    public ApiResult<AudiobookDTO> createAudioBook(AudiobookDTO audiobookDTO) {

        Audiobook audiobook = audiobookMapper.toEntity(audiobookDTO, entityManager);
        audiobookRepository.save(audiobook);

        if (audiobookDTO.getAuthorIds() != null) {
            for (Integer authorId : audiobookDTO.getAuthorIds()) {
                Author author = entityManager.getReference(Author.class, authorId);
                AudioBookAuthors audioBookAuthors = new AudioBookAuthors();
                audioBookAuthors.setAuthor(author);
                audioBookAuthors.setAudiobook(audiobook);
                audioBookAuthorsRepository.save(audioBookAuthors);
            }
        }

        if (audiobookDTO.getGenreIds() != null) {
            for (Integer genreId : audiobookDTO.getGenreIds()) {
                Genre genre = entityManager.getReference(Genre.class, genreId);
                AudioBookGenres audioBookGenres = new AudioBookGenres();
                audioBookGenres.setGenre(genre);
                audioBookGenres.setAudiobook(audiobook);
                audioBookGenresRepository.save(audioBookGenres);
            }
        }

        AudiobookDTO savedDTO = audiobookMapper.toDTO(audiobook);
        populateAssociations(audiobook, savedDTO);

        return ApiResult.success(savedDTO);
    }

    @Override
    @Transactional
    public ApiResult<AudiobookDTO> updateAudioBook(Integer id, AudiobookDTO audiobookDTO) {
        return audiobookRepository.findById(id)
                .map(existingAudiobook -> {
                    audiobookMapper.updateAudiobookFromDTO(audiobookDTO, existingAudiobook);
                    audiobookRepository.save(existingAudiobook);
                    AudiobookDTO updatedDto = audiobookMapper.toDTO(existingAudiobook);
                    populateAssociations(existingAudiobook, updatedDto);
                    return ApiResult.success("Audiobook updated successfully", updatedDto);
                })
                .orElse(ApiResult.error("Audiobook not found with id: " + id));
    }

    @Override
    @Transactional
    public ApiResult<Object> deleteAudioBook(Integer id) {
        Optional<Audiobook> optionalAudiobook = audiobookRepository.findById(id);

        if (optionalAudiobook.isEmpty())
            return ApiResult.error("Audiobook not found with id: " + id);

        Audiobook audiobook = optionalAudiobook.get();
        audiobook.setDeleted(true);

        audiobookRepository.save(audiobook);
        return ApiResult.success("Audiobook deleted successfully");
    }

    @Override
    public ApiResult<List<AuthorDTO>> getAuthorsByAudiobookId(Integer id) {
        Optional<Audiobook> optionalAudiobook = audiobookRepository.findById(id);

        if (optionalAudiobook.isEmpty())
            return ApiResult.error("Audiobook not found with id: " + id);

        List<AudioBookAuthors> audioBookAuthors = audioBookAuthorsRepository.findByAudiobook_Id(id);

        List<AuthorDTO> authorDTOS = audioBookAuthors
                .stream()
                .map(oneAudioBookAuthors ->
                        authorMapper.toDTO(oneAudioBookAuthors.getAuthor())
                )
                .toList();

        return ApiResult.success(authorDTOS);
    }

    @Override
    public ApiResult<List<GenreDTO>> getGenresByAudioBookId(Integer id) {
        Optional<Audiobook> optionalAudiobook = audiobookRepository.findById(id);

        if (optionalAudiobook.isEmpty())
            return ApiResult.error("Audiobook not found with id: " + id);

        List<AudioBookGenres> audioBookGenres = audioBookGenresRepository.findByAudiobook_Id(id);

        List<GenreDTO> genreDTOS = audioBookGenres
                .stream()
                .map(oneAudioBookGenres -> genreMapper.toDTO(oneAudioBookGenres.getGenre()))
                .toList();

        return ApiResult.success(genreDTOS);
    }

    @Override
    public ApiResult<AudioFileDTO> getAudioFileByAudioBookId(Integer id) {
        Audiobook audiobook = audiobookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audiobook not found"));

        AudioFile audioFile = audiobook.getAudioFile();

        if (audioFile == null)
            throw new RuntimeException("Audio file not found with id: " + id + " for audiobook: " + audiobook.getId());

        AudioFileDTO audioFileDTO = audioFileMapper.toDTO(audioFile);

        return ApiResult.success(audioFileDTO);
    }

    private void populateAssociations(Audiobook audiobook, AudiobookDTO dto) {
        List<Integer> authorIds = audioBookAuthorsRepository.findByAudiobook_Id(audiobook.getId())
                .stream()
                .map(audioBookAuthors -> audioBookAuthors.getAuthor().getId())
                .toList();

        dto.setAuthorIds(authorIds);

        List<Integer> genreIds = audioBookGenresRepository.findByAudiobook_Id(audiobook.getId())
                .stream()
                .map(audioBookGenres -> audioBookGenres.getGenre().getId())
                .toList();

        dto.setGenreIds(genreIds);
    }

}
