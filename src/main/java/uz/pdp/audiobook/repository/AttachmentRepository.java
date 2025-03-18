package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.Attachment;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    Optional<Attachment> findByIdAndDeletedFalse(Integer id);

    List<Attachment> findByDeletedFalse();

}