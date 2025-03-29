package uz.pdp.audiobook.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudioFileDTO;

public interface AudioFileService {
    ApiResult<AudioFileDTO> uploadAudioFile(MultipartFile file, Integer audioBookId);

    ApiResult<AudioFileDTO> updateAudioFile(Integer id, MultipartFile file);

    ResponseEntity<Resource> downloadAudioFile(Integer id);

    ApiResult<Object> deleteAudioFile(Integer id);
}
