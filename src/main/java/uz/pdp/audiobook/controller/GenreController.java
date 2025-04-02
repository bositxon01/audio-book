package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.GenreDTO;
import uz.pdp.audiobook.payload.withoutId.GenreDto;
import uz.pdp.audiobook.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
@Tag(name = "Genre API", description = "Genre CRUD API")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<ApiResult<GenreDTO>> createGenre(@Valid @RequestBody GenreDto genreDto) {
        ApiResult<GenreDTO> apiResult = genreService.createGenre(genreDto);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResult<List<GenreDTO>>> createGenres(@Valid @RequestBody List<GenreDto> genreDtoList) {
        ApiResult<List<GenreDTO>> apiResult = genreService.createGenres(genreDtoList);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<GenreDTO>> getGenre(@PathVariable Integer id) {
        ApiResult<GenreDTO> apiResult = genreService.getGenre(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<GenreDTO>>> getAllGenres() {
        ApiResult<List<GenreDTO>> apiResult = genreService.getAllGenre();
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<GenreDTO>> updateGenre(@PathVariable Integer id,
                                                           @Valid @RequestBody GenreDto genreDto) {
        ApiResult<GenreDTO> apiResult = genreService.updateGenre(id, genreDto);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteGenre(@PathVariable Integer id) {
        ApiResult<Object> apiResult = genreService.deleteGenre(id);
        return ResponseEntity.ok(apiResult);
    }

}
