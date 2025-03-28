package uz.pdp.audiobook.service;

import jakarta.validation.Valid;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.UserProgressDTO;

import java.util.List;

public interface UserProgressService {

    ApiResult<UserProgressDTO> getUserProgress(Integer id);

    ApiResult<List<UserProgressDTO>> getAllUserProgress();

    ApiResult<UserProgressDTO> createUserProgress(@Valid UserProgressDTO userProgressDTO);

    ApiResult<UserProgressDTO> updateUserProgress(Integer id, @Valid UserProgressDTO userProgressDTO);

    ApiResult<Object> deleteUserProgress(Integer id);

}
