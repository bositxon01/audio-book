package uz.pdp.audiobook.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AudioFileDTO {

    private Integer id;

    private String fileUrl;

    private Integer durationSeconds;

}