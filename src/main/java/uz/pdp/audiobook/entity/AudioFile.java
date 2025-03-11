package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AudioFile extends AbsIntegerEntity {

    @ManyToOne
    private Audiobook audiobook;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private Integer partNumber;

    @Column(nullable = false)
    private Integer durationSeconds;
}
