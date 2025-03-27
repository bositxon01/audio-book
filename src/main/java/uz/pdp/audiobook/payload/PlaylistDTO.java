package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistDTO {

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotBlank(message = "Playlist name cannot be blank")
    private String playlistName;

    @NotNull(message = "Audiobooks list cannot be null")
    private List<Integer> audiobooksId;

}
