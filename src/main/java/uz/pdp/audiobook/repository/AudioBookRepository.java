package uz.pdp.audiobook.repository;

import uz.pdp.audiobook.entity.Audiobook;

import java.util.List;
import java.util.Optional;

public interface AudioBookRepository {

    Optional<Audiobook> findByIdAndDeletedFalse(Integer id);

    List<Audiobook> findByDeletedFalse(Integer id);

}
