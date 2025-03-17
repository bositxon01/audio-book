package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.audiobook.entity.Attachment;
import uz.pdp.audiobook.mapper.AttachmentMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AttachmentDTO;
import uz.pdp.audiobook.repository.AttachmentRepository;
import uz.pdp.audiobook.service.AttachmentService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Value("${app.files.baseFolder}")
    private String baseFolder;

    private static final long MAX_FILE_SIZE = 100L * 1024 * 1024;

    @Override
    public ApiResult<AttachmentDTO> upload(MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty())
                return ApiResult.error("File is empty");

            String contentType = multipartFile.getContentType();
            long fileSize = multipartFile.getSize();

            if (fileSize > MAX_FILE_SIZE)
                return ApiResult.error("File is too large");

            LocalDate now = LocalDate.now();

            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();

            String originalFilename = multipartFile.getOriginalFilename();

            String filename = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFilename);

            Path path = Paths.get(baseFolder)
                    .resolve(String.valueOf(year))
                    .resolve(String.valueOf(month))
                    .resolve(String.valueOf(day));

            Files.createDirectories(path);

            path = path.resolve(filename);

            Files.copy(multipartFile.getInputStream(), path);

            Attachment attachment = Attachment.builder()
                    .filename(filename)
                    .contentType(contentType)
                    .originalFilename(originalFilename)
                    .size(fileSize)
                    .path(path.toString())
                    .build();

            attachmentRepository.save(attachment);

            return ApiResult.success(attachmentMapper.toDTO(attachment));
        } catch (IOException e) {
            return ApiResult.error("Error occurred while uploading file: " + e.getMessage());
        }
    }

    @Override
    public ApiResult<AttachmentDTO> getAttachment(Integer id) {
        return attachmentRepository.findByIdAndDeletedFalse(id)
                .map(attachmentMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Attachment not found with id: " + id));
    }

    @Override
    public ResponseEntity<Resource> download(Integer id) {
        try {
            Attachment attachment = attachmentRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new RuntimeException("Attachment not found with id: " + id));

            Path filePath = Paths.get(attachment.getPath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            String encodedFilename = java.net.URLEncoder.encode(attachment.getOriginalFilename(), StandardCharsets.UTF_8).replace("+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
