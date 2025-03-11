package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ListeningHistory extends AbsIntegerEntity {

    @ManyToOne
    private User user; // `Integer user_id` o‘rniga `User user`

    @ManyToOne
    private Audiobook audiobook; // `Integer audiobook_id` o‘rniga `Audiobook audiobook`

    @Column(nullable = false)
    private Timestamp lastListeningTime; // `last_listening_time` → `lastListeningTime` qilib o‘zgartirildi

    @Column(nullable = false)
    private Integer progressPercentage; // `progress` → `progressPercentage` qilib aniqroq nom berildi

    @Column(nullable = false)
    private boolean completed; // `completed` maydoni saqlab qolindi

}
