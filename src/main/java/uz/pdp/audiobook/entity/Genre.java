package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "genre",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_active_genre_name", columnNames = {"name", "deleted"}
        ))

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE genre SET deleted = true WHERE id = ?")
public class Genre extends AbsIntegerEntity {

    @ToString.Include
    @Column(unique = true, nullable = false, length = 50)
    private String name;

}
