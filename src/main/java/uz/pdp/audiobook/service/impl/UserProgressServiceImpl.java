package uz.pdp.audiobook.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.audiobook.mapper.UserProgressMapper;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.entity.UserProgress;
import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.UserProgressDTO;
import uz.pdp.audiobook.repository.AudiobookRepository;
import uz.pdp.audiobook.repository.UserProgressRepository;
import uz.pdp.audiobook.repository.UserRepository;
import uz.pdp.audiobook.service.UserProgressService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements UserProgressService {

    private final UserProgressRepository userProgressRepository;
    private final UserProgressMapper userProgressMapper;
    private final UserRepository userRepository;
    private final AudiobookRepository audiobookRepository;

    @Override
    public ApiResult<UserProgressDTO> getUserProgress(Integer id) {
        return userProgressRepository.findById(id)
                .map(userProgressMapper::toDTO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("User not found with id: " + id));
    }

    @Override
    public ApiResult<List<UserProgressDTO>> getAllUserProgress() {
        List<UserProgressDTO> userProgressDTOS = userProgressRepository.findAll()
                .stream()
                .map(userProgressMapper::toDTO)
                .toList();
        return ApiResult.success(userProgressDTOS);
    }

    @Override
    @Transactional
    public ApiResult<UserProgressDTO> createUserProgress(UserProgressDTO userProgressDTO) {
        UserProgress userProgress = userProgressMapper.toEntity(userProgressDTO);

        Integer userId = userProgressDTO.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        user.setId(userId);
        userProgress.setUser(user);

        Integer audiobookId = userProgressDTO.getAudiobookId();

        Audiobook audiobook = audiobookRepository.findById(audiobookId)
                .orElseThrow(() -> new RuntimeException("Audiobook not found with id: " + audiobookId));

        userProgress.setAudiobook(audiobook);

        userProgressRepository.save(userProgress);
        return ApiResult.success(userProgressMapper.toDTO(userProgress));
    }

    @Override
    public ApiResult<UserProgressDTO> updateUserProgress(Integer id, UserProgressDTO userProgressDTO) {
        return userProgressRepository.findById(id)
                .map(existingUserProgress -> {
                    userProgressMapper.updateUserProgressFromDTO(userProgressDTO, existingUserProgress);

                    Integer userId = userProgressDTO.getUserId();
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

                    existingUserProgress.setUser(user);

                    Integer audiobookId = userProgressDTO.getAudiobookId();

                    Audiobook audiobook = audiobookRepository.findById(audiobookId)
                            .orElseThrow(() -> new RuntimeException("Audiobook not found with id: " + audiobookId));

                    existingUserProgress.setAudiobook(audiobook);

                    userProgressRepository.save(existingUserProgress);
                    return ApiResult.success(
                            "User progress updated successfully",
                            userProgressMapper.toDTO(existingUserProgress)
                    );
                })
                .orElse(ApiResult.error("User progress not found with id: " + id));
    }

    @Override
    public ApiResult<Object> deleteUserProgress(Integer id) {
        Optional<UserProgress> optionalUserProgress = userProgressRepository.findById(id);

        if (optionalUserProgress.isEmpty())
            return ApiResult.error("User progress not found with id: " + id);

        UserProgress userProgress = optionalUserProgress.get();
        userProgress.setDeleted(true);
        userProgressRepository.save(userProgress);

        return ApiResult.success("User progress deleted successfully");
    }
}
