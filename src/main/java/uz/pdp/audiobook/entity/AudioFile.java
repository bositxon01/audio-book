package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @Column(nullable = false)
    private String fileUrl; // Fayl URL

    @Column(nullable = false)
    private Integer durationSeconds; // Qismning davomiyligi (sekundlarda)

    @OneToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash

}
