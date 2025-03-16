package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "audiobook")
@Entity
public class AudioFile extends AbsIntegerEntity {

    @ManyToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash

    @Column(nullable = false)
    private String fileUrl; // Fayl URL

    @Column(nullable = false)
    private Integer partNumber; // Qism tartib raqami

    @Column(nullable = false)
    private Integer durationSeconds; // Qismning davomiyligi (sekundlarda)
}
