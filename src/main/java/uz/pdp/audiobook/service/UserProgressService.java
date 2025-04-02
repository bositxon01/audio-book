package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.UserProgressDTO;
import uz.pdp.audiobook.payload.withoutId.UserProgressDto;

import java.util.List;

public interface UserProgressService {

    ApiResult<UserProgressDTO> getUserProgress(Integer id);

    ApiResult<List<UserProgressDTO>> getAllUserProgress();

    ApiResult<UserProgressDTO> createUserProgress(UserProgressDto userProgressDto);

    ApiResult<UserProgressDTO> updateUserProgress(Integer id, UserProgressDto userProgressDto);

    ApiResult<Object> deleteUserProgress(Integer id);

}
