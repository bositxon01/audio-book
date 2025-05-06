package uz.pdp.audiobook.service;

import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;

import java.util.List;

public interface AudiobookSearchService {
    ApiResult<List<AudiobookDTO>> searchByExactMatch(String title, String description);
    ApiResult<List<AudiobookDTO>> searchByKeyword(String keyword);

    ApiResult<List<Audiobook>> searchByTitleAndDescription(String title, String description);
}
