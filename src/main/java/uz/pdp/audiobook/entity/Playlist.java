package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "audiobooks"})
@Entity

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE playlist SET deleted = true WHERE id = ?")
public class Playlist extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String playlistName;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistAudiobooks> audiobooks;

}
