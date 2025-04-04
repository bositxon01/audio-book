package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.*;
import uz.pdp.audiobook.payload.withoutId.AudiobookDto;

import java.util.List;

public interface AudioBookService {

    ApiResult<AudiobookDTO> getAudioBookById(Integer id);

    ApiResult<List<AudiobookDTO>> getAudioBooks();

    ApiResult<AudiobookDTO> createAudioBook(AudiobookDto audiobookDto);

    ApiResult<AudiobookDTO> updateAudioBook(Integer id, AudiobookDto audiobookDto);

    ApiResult<Object> deleteAudioBook(Integer id);

    ApiResult<List<AuthorDTO>> getAuthorsByAudiobookId(Integer id);

    ApiResult<AudioFileDTO> getAudioFileByAudioBookId(Integer id);

    ApiResult<List<GenreDTO>> getGenresByAudioBookId(Integer id);

}
