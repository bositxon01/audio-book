package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Optional<Genre> findByIdAndDeletedFalse(Integer id);

    List<Genre> findByDeletedFalse();

}
