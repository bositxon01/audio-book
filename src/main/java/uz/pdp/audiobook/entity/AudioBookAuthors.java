package uz.pdp.audiobook.entity;

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
@ToString(exclude = {"author", "audiobook"})
@Entity

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE audio_book_authors SET deleted = true WHERE id = ?")
public class AudioBookAuthors extends AbsIntegerEntity {

    @ManyToOne
    private Author author; // Muallif bilan bog‘lash

    @ManyToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash

}
