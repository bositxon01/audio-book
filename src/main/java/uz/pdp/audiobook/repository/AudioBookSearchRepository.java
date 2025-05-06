package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Audiobook;

import java.util.List;

public interface AudioBookSearchRepository extends JpaRepository<Audiobook, Integer> {
    List<Audiobook> findByTitleContainingIgnoreCaseAndDeletedFalse(String title);
}
