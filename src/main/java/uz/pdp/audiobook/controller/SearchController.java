package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search API", description = "Search API")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<ApiResult<List<AudiobookDTO>>> search(@RequestParam(required = false) String query) {
        // query bo'sh yoki null bo'lsa, xatolik qaytarish
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResult.error("Query parameter cannot be empty or null"));
        }

        return ResponseEntity.ok(searchService.search(query));
    }
}
