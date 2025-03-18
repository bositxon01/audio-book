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
@ToString(exclude = {"audiobook", "genre"})
@Entity

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE audio_book_genres SET deleted = true WHERE id = ?")
public class AudioBookGenres extends AbsIntegerEntity {

    @ManyToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash

    @ManyToOne
    private Genre genre; // Janr bilan bog‘lash

}
