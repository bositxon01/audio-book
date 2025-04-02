package uz.pdp.audiobook.payload.withoutId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AudioFileDto {

    private String fileUrl;

    private Integer durationSeconds;

    private String originalFilename;

    private String contentType;

}