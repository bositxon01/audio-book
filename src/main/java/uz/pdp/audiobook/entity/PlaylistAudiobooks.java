package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PlaylistAudiobooks extends AbsIntegerEntity {

    @ManyToOne
    private Playlist playlist; // `Playlist` bilan bog‘lash

    @ManyToOne
    private Audiobook audiobook; // `Audiobook` bilan bog‘lash
}
