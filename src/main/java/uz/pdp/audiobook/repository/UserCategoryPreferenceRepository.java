package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.entity.UserCategoryPreference;

import java.util.List;
import java.util.Optional;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference, Integer> {

    List<UserCategoryPreference> findAllByUser(User user);

    void deleteAllByUser(User user);

    Optional<UserCategoryPreference> findByUserAndCategory(User user, Category category);
}