package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.entity.Playlist;
import uz.pdp.audiobook.entity.PlaylistAudiobooks;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.mapper.PlaylistMapper;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.PlaylistDTO;
import uz.pdp.audiobook.repository.AudiobookRepository;
import uz.pdp.audiobook.repository.PlaylistAudiobooksRepository;
import uz.pdp.audiobook.repository.PlaylistRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.PlaylistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final UserRepository userRepository;
    private final AudiobookRepository audiobookRepository;
    private final PlaylistAudiobooksRepository playlistAudiobooksRepository;

    @Override
    @Transactional
    public ApiResult<PlaylistDTO> createPlaylist(PlaylistDTO playlistDTO) {
        Integer userId = playlistDTO.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlist.setUser(user);
        playlistRepository.save(playlist);

        List<Integer> audiobooksId = playlistDTO.getAudiobooksId();

        List<PlaylistAudiobooks> playlistAudiobooks = new ArrayList<>();

        for (Integer audiobookId : audiobooksId) {
            Audiobook audiobook = audiobookRepository.findById(audiobookId)
                    .orElseThrow(() -> new RuntimeException("Audiobook not found with id: " + audiobookId));

            PlaylistAudiobooks pa = new PlaylistAudiobooks();
            pa.setPlaylist(playlist);
            pa.setAudiobook(audiobook);
            playlistAudiobooksRepository.save(pa);
            playlistAudiobooks.add(pa);
        }

        playlist.setAudiobooks(playlistAudiobooks);
        playlistRepository.save(playlist);

        return ApiResult.success(playlistMapper.toDTO(playlist));
    }

    @Override
    public ApiResult<PlaylistDTO> getPlaylist(Integer id) {
        return playlistRepository.findById(id)
                .map(playlistMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("Playlist not found with id: " + id));
    }

    @Override
    public ApiResult<List<PlaylistDTO>> getAllPlaylists() {
        List<PlaylistDTO> playlists = playlistRepository.findAll()
                .stream()
                .map(playlistMapper::toDTO)
                .collect(Collectors.toList());
        return ApiResult.success(playlists);
    }

    @Override
    @Transactional
    public ApiResult<PlaylistDTO> updatePlaylist(Integer id, PlaylistDTO playlistDTO) {
        return playlistRepository.findById(id)
                .map(existingPlaylist -> {

                    playlistMapper.partialUpdate(playlistDTO, existingPlaylist);

                    if (playlistDTO.getAudiobooksId() != null) {

                        List<PlaylistAudiobooks> currentAssociations = existingPlaylist.getAudiobooks();
                        if (currentAssociations != null && !currentAssociations.isEmpty()) {
                            playlistAudiobooksRepository.deleteAll(currentAssociations);
                        }

                        List<PlaylistAudiobooks> newAssociations = playlistDTO.getAudiobooksId().stream()
                                .map(audiobookId -> {
                                    Audiobook audiobook = audiobookRepository.findById(audiobookId)
                                            .orElseThrow(() -> new RuntimeException("Audiobook not found with id: " + audiobookId));
                                    PlaylistAudiobooks pa = new PlaylistAudiobooks();
                                    pa.setPlaylist(existingPlaylist);
                                    pa.setAudiobook(audiobook);

                                    return playlistAudiobooksRepository.save(pa);
                                })
                                .collect(Collectors.toList());
                        existingPlaylist.setAudiobooks(newAssociations);
                    }

                    playlistRepository.save(existingPlaylist);
                    return ApiResult.success(playlistMapper.toDTO(existingPlaylist));
                })
                .orElse(ApiResult.error("Playlist not found with id: " + id));
    }

    @Override
    @Transactional
    public ApiResult<Object> deletePlaylist(Integer id) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);

        if (optionalPlaylist.isEmpty()) {
            return ApiResult.error("Playlist not found with id: " + id);
        }

        Playlist playlist = optionalPlaylist.get();
        playlist.setDeleted(true);
        playlistRepository.save(playlist);

        return ApiResult.success("Playlist deleted successfully.");
    }
}
