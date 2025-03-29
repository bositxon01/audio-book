package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
