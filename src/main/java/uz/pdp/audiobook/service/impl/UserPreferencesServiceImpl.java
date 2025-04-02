package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.entity.UserCategoryPreference;
import uz.pdp.audiobook.payload.CategoryDTO;
import uz.pdp.audiobook.payload.UserPreferencesDTO;
import uz.pdp.audiobook.payload.UserPreferencesGetDTO;
import uz.pdp.audiobook.repository.CategoryRepository;
import uz.pdp.audiobook.repository.UserCategoryPreferenceRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.UserPreferencesService;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferencesServiceImpl implements UserPreferencesService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryPreferenceRepository userCategoryPreferenceRepository;

    @Override
    public UserPreferencesGetDTO getUserPreferences(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        var preferences = userCategoryPreferenceRepository.findAllByUser(user);

        Set<CategoryDTO> categoryDTOS = preferences.stream()
                .map(pref -> new CategoryDTO(pref.getCategory().getId(), pref.getCategory().getName()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        var userPreferencesDTO = new UserPreferencesGetDTO();
        userPreferencesDTO.setPreferredCategories(categoryDTOS);

        return userPreferencesDTO;
    }

    @Override
    @Transactional
    public void updateUserPreferences(Integer userId, UserPreferencesDTO preferencesDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        userCategoryPreferenceRepository.deleteAllByUser(user);
        userCategoryPreferenceRepository.flush();

        Set<Category> categories = new LinkedHashSet<>();

        Set<Integer> categoryIds = preferencesDTO.getCategoryIds();

        if (Objects.nonNull(categoryIds) && !categoryIds.isEmpty())
            categories.addAll(categoryRepository.findAllById(categoryIds));

        Set<String> preferredCategories = preferencesDTO.getPreferredCategories();

        if (Objects.nonNull(preferredCategories) && !preferredCategories.isEmpty())
            categories.addAll(categoryRepository.findAllByNameIn(preferredCategories));

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
