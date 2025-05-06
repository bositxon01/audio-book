package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Audiobook;

import java.util.List;

@Repository
public interface AudiobookSearchRepository extends JpaRepository<Audiobook, Long> {

    List<Audiobook> findAudiobooksByTitleAndDescription(String title, String description);

    List<Audiobook> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
