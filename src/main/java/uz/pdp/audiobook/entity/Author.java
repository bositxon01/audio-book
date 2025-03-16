package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.audiobook.entity.template.Person;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Author extends Person {

    @Column(columnDefinition = "TEXT")
    private String biography;

}
