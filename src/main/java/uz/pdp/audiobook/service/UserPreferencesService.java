package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.UserPreferencesByIdsDTO;
import uz.pdp.audiobook.payload.UserPreferencesByNamesDTO;
import uz.pdp.audiobook.payload.UserPreferencesGetDTO;

public interface UserPreferencesService {

    UserPreferencesGetDTO getUserPreferences(Integer userId);

    void updateUserPreferencesByIds(Integer userId, UserPreferencesByIdsDTO preferencesDTO);

    void updateUserPreferencesByNames(Integer userId, UserPreferencesByNamesDTO preferencesDTO);

    void skipUserPreferences(Integer userId);

}
