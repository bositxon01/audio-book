package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.mapper.AudiobookSearchMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.repository.AudiobookSearchRepository;
import uz.pdp.audiobook.service.AudiobookSearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AudiobookSearchServiceImpl implements AudiobookSearchService {

    private final AudiobookSearchRepository repository;
    private final AudiobookSearchMapper audiobookSearchMapper;

    @Override
    public ApiResult<List<AudiobookDTO>> searchByExactMatch(String title, String description) {
        var audiobooks = repository.findAudiobooksByTitleAndDescription(title, description)
                .stream()
                .map(audiobookSearchMapper::toDTO)
                .toList();

        return audiobooks.isEmpty()
                ? ApiResult.error("No exact match found")
                : ApiResult.success(audiobooks);
    }

    @Override
    public ApiResult<List<AudiobookDTO>> searchByKeyword(String keyword) {
        var audiobooks = repository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(audiobookSearchMapper::toDTO)
                .toList();

        return audiobooks.isEmpty()
                ? ApiResult.error("No keyword match found")
                : ApiResult.success(audiobooks);
    }

    @Override
    public ApiResult<List<Audiobook>> searchByTitleAndDescription(String title, String description) {
        List<AudiobookDTO> dtoList = repository.findAudiobooksByTitleAndDescription(title, description)
                .stream()
                .map(audiobookSearchMapper::toDTO)
                .toList();

        return dtoList.isEmpty()
                ? ApiResult.error("No audiobooks found with given title and description.")
                : ApiResult.success(dtoList.toString());
    }

}
