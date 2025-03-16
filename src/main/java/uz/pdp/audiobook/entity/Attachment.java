package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class Attachment extends AbsIntegerEntity {

    @NotBlank(message = "Filename cannot be blank")
    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String filename;

    @NotBlank(message = "File original name cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalFilename;

    @NotBlank(message = "Content type cannot be blank")
    @Column(nullable = false)
    private String contentType;

    @NotBlank(message = "Path cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String path;

    @Min(1)
    private Long size;

    @OneToOne(mappedBy = "attachment")
    private Audiobook audiobook;

}
