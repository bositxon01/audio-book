package uz.pdp.audiobook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.AudiobookDTO;
import uz.pdp.audiobook.service.AudiBookSearchService;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final AudiBookSearchService searchService;

    @GetMapping
    public ApiResult<AudiobookDTO> searchAudiobooks(@RequestParam String keyword) {
        return searchService.search(keyword);
    }
}
