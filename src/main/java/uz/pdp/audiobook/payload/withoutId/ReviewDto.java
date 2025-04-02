package uz.pdp.audiobook.payload.withoutId;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {

    @NotNull(message = "User id cannot be null")
    private Integer userId;

    @NotNull(message = "Audiobook id cannot be null")
    private Integer audioBookId;

    private String content;

    @NotNull(message = "Rating cannot be null")
    @Min(1)
    @Max(5)
    private Integer rating;

}
