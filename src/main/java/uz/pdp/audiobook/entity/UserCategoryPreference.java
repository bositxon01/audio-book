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
@ToString
@Entity
@Table(name = "user_category_preference",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_active_user_category",
                columnNames = {"user_id", "category_id", "deleted"}
        )
)

@SQLDelete(sql = "UPDATE user_category_preference SET deleted = true WHERE id = ?")
@SQLRestriction(value = "deleted = false")
public class UserCategoryPreference extends AbsIntegerEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;
}
