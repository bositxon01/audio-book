package uz.pdp.audiobook.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.audiobook.payload.AttachmentDTO;

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

}