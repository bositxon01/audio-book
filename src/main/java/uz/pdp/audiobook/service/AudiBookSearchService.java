package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;

public interface AudiBookSearchService {

    ApiResult<AudiobookDTO> search(String title);

}
