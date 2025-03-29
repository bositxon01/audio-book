package uz.pdp.audiobook.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AudioFileDTO {

    private Integer id;

    private String fileUrl;

    private Integer durationSeconds;

    private String originalFilename;

    private String contentType;

}