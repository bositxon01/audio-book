package uz.pdp.audiobook.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.audiobook.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndDeletedFalse(String username);

    Optional<User> findByUsernameAndDeletedFalse(@NotBlank @Email String username);

}
