package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "audiobook"})
@Entity
@Table(name = "user_progress",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_active_user_progress",
                columnNames = {"user_id", "audiobook_id", "deleted"}
        )
)

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE user_progress SET deleted = true WHERE id = ?")
public class UserProgress extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "audiobook_id", nullable = false)
    private Audiobook audiobook;

    @Min(0)
    private Integer lastAudioFilePosition; // Position in seconds where the user stopped

    @Min(1)
    private Integer lastPage;  // The last page the user viewed

    @Column(nullable = false)
    private boolean completed;

}
