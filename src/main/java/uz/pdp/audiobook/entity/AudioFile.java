package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "audiobook")
@Entity

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE audio_file SET deleted = true WHERE id = ?")
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
