package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Genre extends AbsIntegerEntity {

    @ToString.Include
    @Column(unique = true, nullable = false, length = 100)
    private String name; // Janr nomi
}
