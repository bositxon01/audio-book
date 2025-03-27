package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.entity.UserCategoryPreference;
import uz.pdp.audiobook.payload.UserPreferencesDTO;
import uz.pdp.audiobook.repository.CategoryRepository;
import uz.pdp.audiobook.repository.UserCategoryPreferenceRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.UserPreferencesService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferencesServiceImpl implements UserPreferencesService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryPreferenceRepository userCategoryPreferenceRepository;

    @Override
    public UserPreferencesDTO getUserPreferences(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<UserCategoryPreference> preferences = userCategoryPreferenceRepository.findAllByUser(user);

        Set<Integer> categoryIds = preferences.stream()
                .map(pref -> pref.getCategory().getId())
                .collect(Collectors.toSet());

        UserPreferencesDTO dto = new UserPreferencesDTO();
        dto.setCategoryIds(categoryIds);

        return dto;
    }

    @Override
    @Transactional
    public void updateUserPreferences(Integer userId, UserPreferencesDTO preferencesDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        userCategoryPreferenceRepository.deleteAllByUser(user);

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(preferencesDTO.getCategoryIds()));
        if (categories.size() < 3)
            throw new RuntimeException("You must choose at least three valid categories");

        for (Category category : categories) {
            UserCategoryPreference preference = new UserCategoryPreference();
            preference.setUser(user);
            preference.setCategory(category);
            userCategoryPreferenceRepository.save(preference);
        }

        user.setPreferencesConfigured(true);
        user.setPreferencesSkipped(false);

        userRepository.save(user);
    }

    @Override
    public void skipUserPreferences(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        userCategoryPreferenceRepository.deleteAllByUser(user);

        user.setPreferencesConfigured(true);
        user.setPreferencesSkipped(true);

        userRepository.save(user);
    }
}
