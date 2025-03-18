package uz.pdp.audiobook.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AttachmentDTO;

import java.util.List;

public interface AttachmentService {

    ApiResult<AttachmentDTO> upload(MultipartFile file);

    ApiResult<AttachmentDTO> getAttachmentById(Integer id);

    ApiResult<List<AttachmentDTO>> getAllAttachments();

    ResponseEntity<Resource> download(Integer id);

}
