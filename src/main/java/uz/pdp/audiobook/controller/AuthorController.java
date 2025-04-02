package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.payload.withoutId.AuthorDto;
import uz.pdp.audiobook.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
@Tag(name = "Author API", description = "Author CRUD API")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResult<AuthorDTO>> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        ApiResult<AuthorDTO> apiResult = authorService.createAuthor(authorDto);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<AuthorDTO>> getAuthor(@PathVariable Integer id) {
        ApiResult<AuthorDTO> apiResult = authorService.getAuthor(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<AuthorDTO>>> getAllAuthors() {
        ApiResult<List<AuthorDTO>> apiResult = authorService.getAllAuthors();
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<AuthorDTO>> updateAuthor(@PathVariable Integer id,
                                                             @Valid @RequestBody AuthorDto authorDto) {
        ApiResult<AuthorDTO> apiResult = authorService.updateAuthor(id, authorDto);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteAuthor(@PathVariable Integer id) {
        ApiResult<Object> apiResult = authorService.deleteAuthor(id);
        return ResponseEntity.ok(apiResult);
    }

}
