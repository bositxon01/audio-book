package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.AudioFileDTO;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;

import java.util.List;

public interface AudioBookService {

    ApiResult<AudiobookDTO> getAudioBookById(Integer id);

    ApiResult<List<AudiobookDTO>> getAudioBooks();

    ApiResult<AudiobookDTO> createAudioBook(AudiobookDTO audiobookDTO);

    ApiResult<AudiobookDTO> updateAudioBook(Integer id, AudiobookDTO audiobookDTO);

    ApiResult<Object> deleteAudioBook(Integer id);

    ApiResult<List<AuthorDTO>> getAuthorsByAudiobookId(Integer id);

    ApiResult<AudioFileDTO> getAudioFileByAudioBookId(Integer id);
}
