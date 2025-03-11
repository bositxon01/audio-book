package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AudioBookAuthors extends AbsIntegerEntity {

    @ManyToOne
    private Author author; // `Author` bilan bog‘lash

    @ManyToOne
    private Audiobook audiobook; // `Audiobook` bilan bog‘lash
}
