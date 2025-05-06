package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.service.AudiobookSearchService;

import java.util.List;

@RestController
@RequestMapping("/api/audiobook-search")
@RequiredArgsConstructor
@Tag(name = "Audiobook Search API", description = "Search Audiobooks by title and description")
public class AudiobookSearchController {

    private final AudiobookSearchService audiobookSearchService;

    @GetMapping("/exact")
    public ResponseEntity<ApiResult<List<AudiobookDTO>>> searchExact(
            @RequestParam String title,
            @RequestParam String description) {
        return ResponseEntity.ok(audiobookSearchService.searchByExactMatch(title, description));
    }

    @GetMapping("/keyword")
    public ResponseEntity<ApiResult<List<AudiobookDTO>>> searchByKeyword(@RequestParam String q) {
        return ResponseEntity.ok(audiobookSearchService.searchByKeyword(q));
    }
}
