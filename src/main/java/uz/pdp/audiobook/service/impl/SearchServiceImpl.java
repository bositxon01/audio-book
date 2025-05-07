package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.mapper.AudiobookMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.repository.SearchRepository;
import uz.pdp.audiobook.service.SearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final AudiobookMapper audiobookMapper;

    @Override
    public ApiResult<List<AudiobookDTO>> search(String query) {
        List<Audiobook> audiobooks = searchRepository
                .searchByTitleOrDescriptionOrAuthorName(query.toLowerCase());

        List<AudiobookDTO> dtoList = audiobooks.stream()
                .map(audiobookMapper::toDTO)
                .toList();

        return ApiResult.success(dtoList);
    }
}
