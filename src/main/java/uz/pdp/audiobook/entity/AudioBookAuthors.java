package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"author", "audiobook"})
@Entity
public class AudioBookAuthors extends AbsIntegerEntity {

    @ManyToOne
    private Author author; // Muallif bilan bog‘lash

    @ManyToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash
}
