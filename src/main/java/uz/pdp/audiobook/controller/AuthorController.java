package uz.pdp.audiobook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AuthorDTO;
import uz.pdp.audiobook.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResult<AuthorDTO>> createAuthor(@RequestBody AuthorDTO authorDTO) {
        ApiResult<AuthorDTO> result = authorService.createAuthor(authorDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<AuthorDTO>> getAuthor(@PathVariable Long id) {
        ApiResult<AuthorDTO> result = authorService.getAuthor(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<AuthorDTO>>> getAllAuthors() {
        ApiResult<List<AuthorDTO>> result = authorService.getAllAuthors();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<AuthorDTO>> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        ApiResult<AuthorDTO> result = authorService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deleteAuthor(@PathVariable Long id) {
        ApiResult<Object> result = authorService.deleteAuthor(id);
        return ResponseEntity.ok(result);
    }
}
