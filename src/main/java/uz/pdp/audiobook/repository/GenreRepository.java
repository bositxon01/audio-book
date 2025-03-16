package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Author;
import uz.pdp.audiobook.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Optional<Genre> findByIdAndDeletedFalse(Integer id);
    List<Genre> findByDeletedFalse();

}
