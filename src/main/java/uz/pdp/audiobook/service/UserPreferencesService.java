package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.UserPreferencesDTO;
import uz.pdp.audiobook.payload.UserPreferencesGetDTO;

public interface UserPreferencesService {

    UserPreferencesGetDTO getUserPreferences(Integer userId);

    void updateUserPreferences(Integer userId, UserPreferencesDTO preferencesDTO);

    void skipUserPreferences(Integer userId);

}
