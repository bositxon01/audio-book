package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.UserPreferencesDTO;

public interface UserPreferencesService {

    UserPreferencesDTO getUserPreferences(Integer userId);

    void updateUserPreferences(Integer userId, UserPreferencesDTO preferencesDTO);

    void skipUserPreferences(Integer userId);

}
