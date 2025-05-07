package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.audiobook.entity.Audiobook;

import java.util.List;

public interface SearchRepository extends JpaRepository<Audiobook, Integer> {

    @Query("""
                SELECT DISTINCT a FROM Audiobook a
                LEFT JOIN AudioBookAuthors aba ON a.id = aba.audiobook.id
                LEFT JOIN Author au ON aba.author.id = au.id
                WHERE LOWER(a.title) LIKE %:query%
                   OR LOWER(a.description) LIKE %:query%
                   OR LOWER(au.firstName) LIKE %:query%
                   OR LOWER(au.lastName) LIKE %:query%
                   OR LOWER(CONCAT(au.firstName, ' ', au.lastName)) LIKE %:query%
            """)
    List<Audiobook> searchByTitleOrDescriptionOrAuthorName(@Param("query") String query);
}
