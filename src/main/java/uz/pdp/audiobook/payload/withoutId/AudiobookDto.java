package uz.pdp.audiobook.payload.withoutId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AudiobookDto {

    @NotBlank
    private String title;

    private String description;

    private Integer duration;

    @NotNull
    private Integer coverImageId;

    @NotNull
    private Integer bookAttachmentId;

    @NotNull
    private Integer categoryId;

    private List<Integer> authorIds;

    private List<Integer> genreIds;

}