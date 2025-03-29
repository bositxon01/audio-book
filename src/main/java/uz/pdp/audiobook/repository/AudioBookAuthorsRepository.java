package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.AudioBookAuthors;

import java.util.List;

public interface AudioBookAuthorsRepository extends JpaRepository<AudioBookAuthors, Integer> {

    List<AudioBookAuthors> findByAudiobook_Id(Integer audiobookId);

}