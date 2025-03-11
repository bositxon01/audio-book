package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Playlist extends AbsIntegerEntity {

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String playlistName;
}
