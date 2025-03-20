package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Audiobook;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudiobookRepository extends JpaRepository<Audiobook, Integer> {

    Optional<Audiobook> findByIdAndDeletedFalse(Integer id);

    List<Audiobook> findByDeletedFalse();

}
