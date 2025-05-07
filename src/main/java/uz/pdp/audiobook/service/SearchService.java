package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;

import java.util.List;

public interface SearchService {

    ApiResult<List<AudiobookDTO>> search(String query);

}
