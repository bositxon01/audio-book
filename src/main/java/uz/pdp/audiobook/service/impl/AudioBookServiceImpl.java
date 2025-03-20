package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.AudioFile;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.mapper.AudioFileMapper;
import uz.pdp.audiobook.payload.AudioFileDTO;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.mapper.AudiobookMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.repository.AudiobookRepository;
import uz.pdp.audiobook.service.AudioBookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AudioBookServiceImpl implements AudioBookService {

    private final AudiobookRepository audiobookRepository;
    private final AudiobookMapper audiobookMapper;
    private final AudioFileMapper audioFileMapper;

    @Override
    public ApiResult<AudiobookDTO> getAudioBookById(Integer id) {
        return null;
    }

    @Override
    public ApiResult<List<AudiobookDTO>> getAudioBooks() {
        return null;
    }

    @Override
    public ApiResult<AudiobookDTO> createAudioBook(AudiobookDTO audiobookDTO) {
        return null;
    }

    @Override
    public ApiResult<AudiobookDTO> updateAudioBook(Integer id, AudiobookDTO audiobookDTO) {
        return null;
    }

    @Override
    public ApiResult<Object> deleteAudioBook(Integer id) {
        return null;
    }

    @Override
    public ApiResult<List<AuthorDTO>> getAuthorsByAudiobookId(Integer id) {
        return null;
    }

    @Override
    public ApiResult<AudioFileDTO> getAudioFileByAudioBookId(Integer id) {
        Audiobook audiobook = audiobookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audiobook not found"));

        AudioFile audioFile = audiobook.getAudioFile();

        if (audioFile == null) {
            throw new RuntimeException("Audio file not found with id: " + id + " for audiobook: " + audiobook.getId());
        }

        AudioFileDTO audioFileDTO = audioFileMapper.toDTO(audioFile);

        return ApiResult.success(audioFileDTO);
    }
}
