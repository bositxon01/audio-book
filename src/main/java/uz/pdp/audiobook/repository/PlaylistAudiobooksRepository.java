package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.PlaylistAudiobooks;

public interface PlaylistAudiobooksRepository extends JpaRepository<PlaylistAudiobooks, Integer> {
}