package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.entity.Playlist;
import uz.pdp.audiobook.entity.PlaylistAudiobooks;

import java.util.List;
import java.util.Optional;

public interface PlaylistAudiobooksRepository extends JpaRepository<PlaylistAudiobooks, Integer> {

    Optional<PlaylistAudiobooks> findByPlaylistAndAudiobook(Playlist playlist, Audiobook audiobook);

    List<PlaylistAudiobooks> findByPlaylist(Playlist playlist);

}