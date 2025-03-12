package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Genre extends AbsIntegerEntity {

    @ToString.Include
    @Column(unique = true, nullable = false, length = 50)
    private String name; // Janr nomi
}
