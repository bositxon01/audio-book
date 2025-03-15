package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
     Optional<Author> findByIdAndDeletedFalse(Integer id);

     List<Author> findByDeletedFalse();
}
