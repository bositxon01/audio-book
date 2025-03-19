package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    private AttachmentDTO coverImage;

    private AttachmentDTO bookAttachment;

    private CategoryDTO category;
}