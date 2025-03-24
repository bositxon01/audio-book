package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDTO {

    @NotNull
    private Integer userId;

    private Integer id;


    @NotNull
    private Integer audioBookId;

    @NotNull
    private String content;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

}
