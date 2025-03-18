package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"playlist", "audiobook"})
@Entity
@Table(name = "playlist_audiobooks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"playlist_id", "audiobook_id"}))

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE playlist_audiobooks SET deleted = true WHERE id = ?")
public class PlaylistAudiobooks extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist; // Playlist bilan bog‘lash

    @ManyToOne
    @JoinColumn(name = "audiobook_id", nullable = false)
    private Audiobook audiobook; // Audiobook bilan bog‘lash

}
