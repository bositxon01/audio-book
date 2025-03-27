package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.UserProgress;

public interface UserProgressRepository extends JpaRepository<UserProgress, Integer> {
}