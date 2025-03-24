package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.AudioBookGenres;

import java.util.List;

public interface AudioBookGenresRepository extends JpaRepository<AudioBookGenres, Integer> {
    List<AudioBookGenres> findByAudiobook_Id(Integer audiobookId);
}