package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

     Optional<Author> findByIdAndDeletedFalse(Integer id);

     List<Author> findByDeletedFalse();

}
