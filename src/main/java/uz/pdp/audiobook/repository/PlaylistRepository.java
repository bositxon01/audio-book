package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Playlist;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    // Faqat active (deleted = false) playlistlarni olish
    List<Playlist> findByDeletedFalse();

    // ID bo'yicha faqat active playlistni olish
    Optional<Playlist> findByIdAndDeletedFalse(Integer id);

    // User ID va playlist nomi bo'yicha playlistni olish
    Optional<Playlist> findByUserIdAndPlaylistNameAndDeletedFalse(Integer userId, String playlistName);
}
