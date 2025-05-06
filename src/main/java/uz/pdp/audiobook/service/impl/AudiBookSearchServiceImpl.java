package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.repository.AudioBookSearchRepository;
import uz.pdp.audiobook.service.AudiBookSearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AudiBookSearchServiceImpl implements AudiBookSearchService {

    private final AudioBookSearchRepository audioBookSearchRepository;
    private final uz.pdp.audiobook.mapper.AudiobookMapper audiobookMapper;

    @Override
    public ApiResult<AudiobookDTO> search(String keyword) {
        List<Audiobook> books = audioBookSearchRepository.findByTitleContainingIgnoreCaseAndDeletedFalse(keyword);
        List<AudiobookDTO> dtoList = books.stream()
                .map(audiobookMapper::toDTO)
                .toList();
        return ApiResult.success((AudiobookDTO) dtoList);
    }

}
