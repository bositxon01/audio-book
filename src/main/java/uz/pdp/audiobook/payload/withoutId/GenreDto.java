package uz.pdp.audiobook.payload.withoutId;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenreDto {

    @NotBlank(message = "Genre name cannot be blank")
    private String name;

}
