package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AudiobookDTO {

    private Integer id;

    @NotBlank
    private String title;

    private String description;

    private Integer duration;

    private Integer coverImageId;

    private Integer bookAttachmentId;

    private Integer categoryId;

    private List<Integer> authorIds;

    private List<Integer> genreIds;

}