package uz.pdp.audiobook.payload;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenreDTO {

    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

}
