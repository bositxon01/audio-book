package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.*;
import uz.pdp.audiobook.service.AudioBookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audiobook")
@Tag(name = "AudioBook API", description = "AudioBook CRUD API")
public class AudioBookController {

    private final AudioBookService audioBookService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<AudiobookDTO>> getAudioBookById(@PathVariable("id") Integer id) {
        ApiResult<AudiobookDTO> apiResult = audioBookService.getAudioBookById(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<AudiobookDTO>>> getAudioBooks() {
        ApiResult<List<AudiobookDTO>> apiResult = audioBookService.getAudioBooks();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}/authors")
    public ResponseEntity<ApiResult<List<AuthorDTO>>> getAuthorsByAudiobookId(@PathVariable("id") Integer id) {
        ApiResult<List<AuthorDTO>> apiResult = audioBookService.getAuthorsByAudiobookId(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}/genres")
    public ResponseEntity<ApiResult<List<GenreDTO>>> getGenresByAudiobookId(@PathVariable("id") Integer id) {
        ApiResult<List<GenreDTO>> apiResult = audioBookService.getGenresByAudioBookId(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}/audio-file")
    public ResponseEntity<ApiResult<AudioFileDTO>> getAudioFileById(@PathVariable("id") Integer id) {
        ApiResult<AudioFileDTO> apiResult = audioBookService.getAudioFileByAudioBookId(id);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping
    public ResponseEntity<ApiResult<AudiobookDTO>> createAudioBook(@Valid @RequestBody AudiobookDTO audiobookDTO) {
        ApiResult<AudiobookDTO> apiResult = audioBookService.createAudioBook(audiobookDTO);
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<AudiobookDTO>> updateAudioBook(@PathVariable Integer id,
                                                                   @Valid @RequestBody AudiobookDTO audiobookDTO) {
        ApiResult<AudiobookDTO> apiResult = audioBookService.updateAudioBook(id, audiobookDTO);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteAudioBook(@PathVariable Integer id) {
        ApiResult<Object> apiResult = audioBookService.deleteAudioBook(id);
        return ResponseEntity.ok(apiResult);
    }

}

