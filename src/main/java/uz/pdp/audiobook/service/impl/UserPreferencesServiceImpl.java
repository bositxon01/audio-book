package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.entity.Category;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.entity.UserCategoryPreference;
import uz.pdp.audiobook.payload.CategoryDTO;
import uz.pdp.audiobook.payload.UserPreferencesByIdsDTO;
import uz.pdp.audiobook.payload.UserPreferencesByNamesDTO;
import uz.pdp.audiobook.payload.UserPreferencesGetDTO;
import uz.pdp.audiobook.repository.CategoryRepository;
import uz.pdp.audiobook.repository.UserCategoryPreferenceRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.UserPreferencesService;

import java.util.*;
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
    public void updateUserPreferencesByIds(Integer userId, UserPreferencesByIdsDTO preferencesDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<UserCategoryPreference> existingPreferences =
                userCategoryPreferenceRepository.findAllByUser(user);

        Set<Integer> existingCategoryIds = existingPreferences.stream()
                .map(pref -> pref.getCategory().getId())
                .collect(Collectors.toSet());

        Set<Integer> newCategoryIds = preferencesDTO.getCategoryIds();

        if (newCategoryIds == null || newCategoryIds.size() < 3) {
            throw new RuntimeException("You must choose at least three valid categories");
        }

        Set<Integer> toDelete = new HashSet<>(existingCategoryIds);
        toDelete.removeAll(newCategoryIds);

        Set<Integer> toAdd = new HashSet<>(newCategoryIds);
        toAdd.removeAll(existingCategoryIds);

        for (UserCategoryPreference pref : existingPreferences) {
            if (toDelete.contains(pref.getCategory().getId())) {
                pref.setDeleted(true);
                userCategoryPreferenceRepository.save(pref);
            }
        }

        if (!toAdd.isEmpty()) {
            List<Category> categoriesToAdd = categoryRepository.findAllById(toAdd);
            if (categoriesToAdd.size() < toAdd.size()) {
                throw new RuntimeException("Some category IDs do not exist in the database");
            }
            addNewPrefCategories(user, categoriesToAdd);
        }

        user.setPreferencesConfigured(true);
        user.setPreferencesSkipped(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserPreferencesByNames(Integer userId, UserPreferencesByNamesDTO preferencesDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<UserCategoryPreference> existingPreferences = userCategoryPreferenceRepository.findAllByUser(user);

        Set<String> existingCategoryNames = existingPreferences.stream()
                .map(pref -> pref.getCategory().getName())
                .collect(Collectors.toSet());

        Set<String> newCategoryNames = preferencesDTO.getPreferredCategories();

        if (newCategoryNames == null || newCategoryNames.size() < 3) {
            throw new RuntimeException("You must choose at least three valid categories");
        }

        Set<String> toDelete = new HashSet<>(existingCategoryNames);
        toDelete.removeAll(newCategoryNames);

        Set<String> toAdd = new HashSet<>(newCategoryNames);
        toAdd.removeAll(existingCategoryNames);

        for (UserCategoryPreference pref : existingPreferences) {
            if (toDelete.contains(pref.getCategory().getName())) {
                pref.setDeleted(true);
                userCategoryPreferenceRepository.save(pref);
            }
        }

        if (!toAdd.isEmpty()) {
            List<Category> categoriesToAdd = categoryRepository.findByNameIn(toAdd);
            if (categoriesToAdd.size() < toAdd.size()) {
                throw new RuntimeException("Some category names do not exist in the database");
            }

            addNewPrefCategories(user, categoriesToAdd);
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

    private void addNewPrefCategories(User user, List<Category> categoriesToAdd) {
        for (Category category : categoriesToAdd) {
            Optional<UserCategoryPreference> optionalPref = userCategoryPreferenceRepository.findByUserAndCategory(user, category);
            UserCategoryPreference pref;
            if (optionalPref.isPresent()) {
                pref = optionalPref.get();
                pref.setDeleted(false);
            } else {
                pref = new UserCategoryPreference();
                pref.setUser(user);
                pref.setCategory(category);
            }
            userCategoryPreferenceRepository.save(pref);
        }
    }
}
