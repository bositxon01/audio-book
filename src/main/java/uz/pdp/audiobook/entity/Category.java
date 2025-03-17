package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
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
                name = "unique_active_categories_name", columnNames = {"name", "deleted"}
        )
)
public class Category extends AbsIntegerEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Audiobook> audiobooks;
}
