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
@ToString
@Entity
@Table(name = "category",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_active_category",
                columnNames = {"name", "deleted"}
        )
)

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id = ?")
public class Category extends AbsIntegerEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Audiobook> audiobooks;
}
