package uz.pdp.audiobook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.entity.UserCategoryPreference;

import java.util.List;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference, Integer> {

    List<UserCategoryPreference> findAllByUser(User user);

    void deleteAllByUser(User user);

}