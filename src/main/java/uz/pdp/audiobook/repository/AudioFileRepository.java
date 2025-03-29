package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.AudioFile;

public interface AudioFileRepository extends JpaRepository<AudioFile, Integer> {
}