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

    @NotNull
    private Integer userId;

    @NotBlank
    private String playlistName;

    private List<Integer> audiobooksId;

}
