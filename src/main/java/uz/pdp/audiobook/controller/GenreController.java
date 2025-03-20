package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.GenreDTO;
import uz.pdp.audiobook.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
@Tag(name = "Genre API", description = "Genre CRUD API")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<ApiResult<GenreDTO>> createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        ApiResult<GenreDTO> result = genreService.createGenre(genreDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<GenreDTO>> getGenre(@PathVariable Integer id) {
        ApiResult<GenreDTO> result = genreService.getGenre(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<GenreDTO>>> getAllGenres() {
        ApiResult<List<GenreDTO>> result = genreService.getAllGenre();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<GenreDTO>> updateGenre(@PathVariable Integer id,
                                                           @Valid @RequestBody GenreDTO genreDTO) {
        ApiResult<GenreDTO> result = genreService.updateGenre(id, genreDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteGenre(@PathVariable Integer id) {
        ApiResult<Object> result = genreService.deleteGenre(id);
        return ResponseEntity.ok(result);
    }
}
