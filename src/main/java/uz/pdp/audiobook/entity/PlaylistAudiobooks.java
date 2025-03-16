package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"playlist", "audiobook"})
@Entity
@Table(name = "playlist_audiobooks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"playlist_id", "audiobook_id"}))
public class PlaylistAudiobooks extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist; // Playlist bilan bog‘lash

    @ManyToOne
    @JoinColumn(name = "audiobook_id", nullable = false)
    private Audiobook audiobook; // Audiobook bilan bog‘lash
}
