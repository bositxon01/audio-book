package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenreDTO {

    private Integer id;

    @NotBlank(message = "Genre name cannot be blank")
    private String name;

}
