package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Audiobook;

import java.util.List;
import java.util.Optional;

public interface AudiobookRepository extends JpaRepository<Audiobook, Long> {
    Optional<Audiobook> findByIdAndDeletedFalse(Integer id);
    List<Audiobook> findByDeletedFalse();
}
