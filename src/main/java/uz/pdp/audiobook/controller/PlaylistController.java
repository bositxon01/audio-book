package uz.pdp.audiobook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.PlaylistDTO;
import uz.pdp.audiobook.payload.withoutId.PlaylistDto;
import uz.pdp.audiobook.service.PlaylistService;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
@Tag(name = "Playlist API", description = "Playlist CRUD API")
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<ApiResult<PlaylistDTO>> createPlaylist(@Valid @RequestBody PlaylistDto playlistDto) {
        ApiResult<PlaylistDTO> apiResult = playlistService.createPlaylist(playlistDto);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<PlaylistDTO>> getPlaylist(@PathVariable Integer id) {
        ApiResult<PlaylistDTO> apiResult = playlistService.getPlaylist(id);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<PlaylistDTO>>> getAllPlaylists() {
        ApiResult<List<PlaylistDTO>> apiResult = playlistService.getAllPlaylists();
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<PlaylistDTO>> updatePlaylist(@PathVariable Integer id,
                                                                 @Valid @RequestBody PlaylistDto playlistDto) {
        ApiResult<PlaylistDTO> apiResult = playlistService.updatePlaylist(id, playlistDto);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deletePlaylist(@PathVariable Integer id) {
        ApiResult<Object> apiResult = playlistService.deletePlaylist(id);
        return ResponseEntity.ok(apiResult);
    }

}
