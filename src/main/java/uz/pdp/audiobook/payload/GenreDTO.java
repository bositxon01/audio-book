package uz.pdp.audiobook.payload;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GenreDTO {

    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

}
