package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"audiobook", "genre"})
@Entity
public class AudioBookGenres extends AbsIntegerEntity {

    @ManyToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash

    @ManyToOne
    private Genre genre; // Janr bilan bog‘lash

}
