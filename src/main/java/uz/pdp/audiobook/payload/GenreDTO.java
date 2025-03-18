package uz.pdp.audiobook.payload;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenreDTO {

    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

}
