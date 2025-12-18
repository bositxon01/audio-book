package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Audiobook;

@Repository
public interface AudiobookRepository extends JpaRepository<Audiobook, Integer> {
}