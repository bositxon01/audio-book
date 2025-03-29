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
import uz.pdp.audiobook.payload.AudioFileDTO;
import uz.pdp.audiobook.service.AudioFileService;

@RestController
@RequestMapping("/api/audio-file")
@RequiredArgsConstructor
@Tag(name = "AudioFile API", description = "Endpoints for uploading, updating, downloading, and deleting audio files")
public class AudioFileController {

    private final AudioFileService audioFileService;

    @Operation(summary = "Upload a file", description = "Uploads file to audiofile")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<AudioFileDTO>> uploadAudioFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("audiobookId") Integer audiobookId) {
        ApiResult<AudioFileDTO> result = audioFileService.uploadAudioFile(file, audiobookId);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<AudioFileDTO>> updateAudioFile(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {
        ApiResult<AudioFileDTO> result = audioFileService.updateAudioFile(id, file);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Download a file", description = "Download file")
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadAudioFile(@PathVariable Integer id) {
        return audioFileService.downloadAudioFile(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteAudioFile(@PathVariable Integer id) {
        ApiResult<Object> result = audioFileService.deleteAudioFile(id);
        return ResponseEntity.ok(result);
    }

}
