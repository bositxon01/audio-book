package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
