package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Playlist;
import uz.pdp.audiobook.mapper.PlaylistMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.PlaylistDTO;
import uz.pdp.audiobook.repository.PlaylistRepository;
import uz.pdp.audiobook.service.PlaylistService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    @Transactional
    public ApiResult<PlaylistDTO> createPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlistRepository.save(playlist);
        return ApiResult.success(playlistMapper.toDTO(playlist));
    }

    @Override
    public ApiResult<PlaylistDTO> getPlaylist(Integer id) {
        return playlistRepository.findByIdAndDeletedFalse(id)
                .map(playlistMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Playlist not found with id: " + id));
    }

    @Override
    public ApiResult<List<PlaylistDTO>> getAllPlaylists() {
        List<PlaylistDTO> playlists = playlistRepository.findByDeletedFalse()
                .stream()
                .map(playlistMapper::toDTO)
                .collect(Collectors.toList());
        return ApiResult.success(playlists);
    }

    @Override
    @Transactional
    public ApiResult<PlaylistDTO> updatePlaylist(Integer id, PlaylistDTO playlistDTO) {
        return playlistRepository.findByIdAndDeletedFalse(id)
                .map(existingPlaylist -> {
                    playlistMapper.partialUpdate(playlistDTO, existingPlaylist);
                    playlistRepository.save(existingPlaylist);
                    return ApiResult.success(playlistMapper.toDTO(existingPlaylist));
                })
                .orElse(ApiResult.error("Playlist not found with id: " + id));
    }

    @Override
    @Transactional
    public ApiResult<Object> deletePlaylist(Integer id) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findByIdAndDeletedFalse(id);

        if (optionalPlaylist.isEmpty()) {
            return ApiResult.error("Playlist not found with id: " + id);
        }

        Playlist playlist = optionalPlaylist.get();
        playlist.setDeleted(true);
        playlistRepository.save(playlist);

        return ApiResult.success("Playlist deleted successfully.");
    }
}
