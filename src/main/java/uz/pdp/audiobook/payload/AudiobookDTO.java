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
public class AudiobookDTO {

    private Integer id;

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