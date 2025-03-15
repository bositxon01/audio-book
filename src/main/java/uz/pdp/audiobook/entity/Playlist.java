package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "audiobooks"})
@Entity
public class Playlist extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistAudiobooks> audiobooks;
}
