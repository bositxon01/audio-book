package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Audiobook extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
    private String title; // Kitob nomi noyob bo‘lishi mumkin

    @Column(columnDefinition = "text") // Uzoq matn uchun
    private String description;

    @ManyToOne
    private Author author; // `Integer author_id` o‘rniga `Author author`

    @Column(nullable = false)
    private Integer durationMinutes; // `duration` → `durationMinutes` qilib o‘zgartirildi

    private String coverUrl; // `cover_image` → `coverUrl` qilib nomi o‘zgartirildi

}
