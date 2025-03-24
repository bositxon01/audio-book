package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.AudioFile;

import java.util.List;
import java.util.Optional;

public interface AudioFileRepository extends JpaRepository<AudioFile, Integer> {

    Optional<AudioFile> findByIdAndDeletedFalse(Integer id);

    List<AudioFile> findByDeletedFalse();


}