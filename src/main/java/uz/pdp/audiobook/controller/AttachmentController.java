package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AttachmentDTO;
import uz.pdp.audiobook.service.AttachmentService;

import java.util.List;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
@Tag(name = "Attachment API", description = "Attachment uploading and downloading")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Operation(summary = "Get one attachment", description = "Gets an attachment by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<AttachmentDTO>> getAttachment(@PathVariable Integer id) {
        ApiResult<AttachmentDTO> apiResult = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "Get all attachments", description = "Gets all attachments")
    @GetMapping
    public ResponseEntity<ApiResult<List<AttachmentDTO>>> getAllAttachments() {
        ApiResult<List<AttachmentDTO>> apiResult = attachmentService.getAllAttachments();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "Upload a file", description = "Uploads file to audiobook")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<AttachmentDTO>> upload(@RequestParam("file") MultipartFile file) {
        ApiResult<AttachmentDTO> apiResult = attachmentService.upload(file);
        return ResponseEntity.ok(apiResult);
    }

    @Operation(summary = "Download a file", description = "Download file")
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) {
        return attachmentService.download(id);
    }

}
