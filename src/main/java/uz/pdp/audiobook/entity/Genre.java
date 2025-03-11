package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Genre extends AbsIntegerEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String name; // Janr nomi

}