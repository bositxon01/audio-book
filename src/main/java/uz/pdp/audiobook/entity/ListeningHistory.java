package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "audiobook"})
@Entity
@Table(name = "listening_history")

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE listening_history SET deleted = true WHERE id = ?")
public class ListeningHistory extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "audiobook_id", nullable = false)
    private Audiobook audiobook;

    @Column(nullable = false, updatable = false)
    private Timestamp lastListeningTime;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Integer progressPercentage;

    @Column(nullable = false)
    private boolean completed;

}
