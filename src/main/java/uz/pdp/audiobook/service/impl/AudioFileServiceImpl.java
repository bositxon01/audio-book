package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.audiobook.entity.AudioFile;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.mapper.AudioFileMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudioFileDTO;
import uz.pdp.audiobook.repository.AudioFileRepository;
import uz.pdp.audiobook.repository.AudiobookRepository;
import uz.pdp.audiobook.service.AudioFileService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioFileServiceImpl implements AudioFileService {

    private final AudioFileRepository audioFileRepository;
    private final AudiobookRepository audiobookRepository;
    private final AudioFileMapper audioFileMapper;

    private static final String AUDIO_FILE_DIRECTORY = "file/uploads/audio/";

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<AudioFileDTO> uploadAudioFile(MultipartFile file, Integer audiobookId) {
        if (file.isEmpty()) {
            return ApiResult.error("File is empty");
        }

        Optional<Audiobook> optionalAudiobook = audiobookRepository.findById(audiobookId);
        if (optionalAudiobook.isEmpty()) {
            return ApiResult.error("Audiobook not found with id: " + audiobookId);
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Path.of(AUDIO_FILE_DIRECTORY + fileName);

            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Audiobook audiobook = optionalAudiobook.get();

            AudioFile audioFile = AudioFile.builder()
                    .audiobook(audiobook)
                    .contentType(file.getContentType())
                    .originalFilename(file.getOriginalFilename())
                    .durationSeconds(getAudioDuration(file))
                    .fileUrl(filePath.toString())
                    .build();

            audioFileRepository.save(audioFile);

            audiobook.setAudioFile(audioFile);
            audiobookRepository.save(audiobook);
            return ApiResult.success(audioFileMapper.toDTO(audioFile));
        } catch (IOException e) {
            return ApiResult.error("File upload failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<AudioFileDTO> updateAudioFile(Integer id, MultipartFile file) {
        Optional<AudioFile> optionalAudioFile = audioFileRepository.findById(id);
        if (optionalAudioFile.isEmpty()) {
            return ApiResult.error("Audio file not found with id: " + id);
        }

        AudioFile audioFile = optionalAudioFile.get();

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Path.of(AUDIO_FILE_DIRECTORY + fileName);

            Files.deleteIfExists(Path.of(audioFile.getFileUrl()));

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            audioFile.setFileUrl(filePath.toString());
            audioFile.setDurationSeconds(getAudioDuration(file));
            audioFileRepository.save(audioFile);

            return ApiResult.success(audioFileMapper.toDTO(audioFile));
        } catch (IOException e) {
            return ApiResult.error("File update failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Resource> downloadAudioFile(Integer id) {
        Optional<AudioFile> optionalAudioFile = audioFileRepository.findById(id);
        if (optionalAudioFile.isEmpty()) {
            throw new RuntimeException("Audio file not found with id: " + id);
        }

        AudioFile audioFile = optionalAudioFile.get();
        try {
            Path filePath = Paths.get(audioFile.getFileUrl())
                    .toAbsolutePath().normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            String encodedFilename = URLEncoder.encode(
                            audioFile.getOriginalFilename(),
                            StandardCharsets.UTF_8)
                    .replace("+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(audioFile.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new RuntimeException("File download error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResult<Object> deleteAudioFile(Integer id) {
        Optional<AudioFile> optionalAudioFile = audioFileRepository.findById(id);
        if (optionalAudioFile.isEmpty())
            return ApiResult.error("Audio file not found with id: " + id);

        AudioFile audioFile = optionalAudioFile.get();

        try {
            Files.deleteIfExists(Path.of(audioFile.getFileUrl()));
        } catch (IOException e) {
            return ApiResult.error("File deletion failed: " + e.getMessage());
        }

        audioFileRepository.delete(audioFile);
        return ApiResult.success("Audio file deleted successfully");
    }

    private Integer getAudioDuration(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Metadata metadata = new Metadata();
            Mp3Parser mp3Parser = new Mp3Parser();

            mp3Parser.parse(inputStream,
                    new BodyContentHandler(),
                    metadata,
                    new ParseContext()
            );

            String durationStr = metadata.get("xmpDM:duration");

            if (durationStr != null) {
                double durationInSeconds = Double.parseDouble(durationStr);
                return (int) Math.round(durationInSeconds);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
