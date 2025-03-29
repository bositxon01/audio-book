package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProgressDTO {

    private Integer id;

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Audiobook ID cannot be null")
    private Integer audiobookId;

    @Min(message = "Last audio file position must be 0 or greater", value = 0)
    private Integer lastAudioFilePosition;

    @Min(message = "Last page must be at least 1", value = 1)
    private Integer lastPage;

    private boolean completed;

}