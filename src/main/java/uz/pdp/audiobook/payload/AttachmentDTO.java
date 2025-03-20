package uz.pdp.audiobook.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentDTO implements Serializable {

    private Integer id;

    @NotBlank(message = "Filename cannot be blank")
    @JsonIgnore
    private String filename;

    @JsonProperty("filename")
    @NotBlank(message = "File original name cannot be blank")
    private String originalFilename;

    @NotBlank(message = "Content type cannot be blank")
    private String contentType;

    @NotBlank(message = "Path cannot be blank")
    private String path;

    @Min(1)
    private Long size;

}