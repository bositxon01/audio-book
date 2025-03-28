package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Audiobook;

public interface AudiobookRepository extends JpaRepository<Audiobook, Integer> {
}