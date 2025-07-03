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
import uz.pdp.audiobook.payload.withoutId.PlaylistDto;
import uz.pdp.audiobook.repository.AudioBookRepository;
import uz.pdp.audiobook.repository.PlaylistAudiobooksRepository;
import uz.pdp.audiobook.repository.PlaylistRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.PlaylistService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final UserRepository userRepository;
    private final AudioBookRepository audiobookRepository;
    private final PlaylistAudiobooksRepository playlistAudiobooksRepository;

    @Override
    @Transactional
    public ApiResult<PlaylistDTO> createPlaylist(PlaylistDto playlistDto) {
        Integer userId = playlistDto.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        Playlist playlist = playlistMapper.toEntity(playlistDto);
        playlist.setUser(user);
        playlistRepository.save(playlist);

        List<Integer> audiobooksId = playlistDto.getAudiobooksId();

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
    public ApiResult<PlaylistDTO> updatePlaylist(Integer id, PlaylistDto playlistDto) {
        return playlistRepository.findById(id)
                .map(existingPlaylist -> {

                    if (playlistDto.getPlaylistName() != null) {
                        existingPlaylist.setPlaylistName(playlistDto.getPlaylistName());
                    }

                    List<PlaylistAudiobooks> currentAssociations = new ArrayList<>(existingPlaylist.getAudiobooks());
                    Set<Integer> existingAudiobookIds = currentAssociations.stream()
                            .map(pa -> pa.getAudiobook().getId())
                            .collect(Collectors.toSet());

                    Set<Integer> newAudiobookIds = new HashSet<>(playlistDto.getAudiobooksId());

                    List<PlaylistAudiobooks> toDelete = currentAssociations.stream()
                            .filter(pa -> !newAudiobookIds.contains(pa.getAudiobook().getId()))
                            .toList();

                    Set<Integer> toAdd = newAudiobookIds.stream()
                            .filter(idToAdd -> !existingAudiobookIds.contains(idToAdd))
                            .collect(Collectors.toSet());

                    for (PlaylistAudiobooks pa : toDelete) {
                        existingPlaylist.removeAudiobook(pa);
                        playlistAudiobooksRepository.delete(pa);
                    }

                    for (Integer audiobookId : toAdd) {
                        Audiobook audiobook = audiobookRepository.findById(audiobookId)
                                .orElseThrow(() -> new RuntimeException("Audiobook not found with id: " + audiobookId));

                        PlaylistAudiobooks pa = new PlaylistAudiobooks();
                        pa.setPlaylist(existingPlaylist);
                        pa.setAudiobook(audiobook);
                        existingPlaylist.addAudiobook(pa);
                    }

                    playlistRepository.save(existingPlaylist);
                    return ApiResult.success(
                            "Playlist updated successfully",
                            playlistMapper.toDTO(existingPlaylist)
                    );
                })
                .orElse(ApiResult.error("Playlist not found with id: " + id));
    }

    @Override
    @Transactional
    public ApiResult<Object> deletePlaylist(Integer id) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);

        if (optionalPlaylist.isEmpty())
            return ApiResult.error("Playlist not found with id: " + id);

        Playlist playlist = optionalPlaylist.get();
        playlist.setDeleted(true);
        playlistRepository.save(playlist);

        return ApiResult.success("Playlist deleted successfully.");
    }

}
