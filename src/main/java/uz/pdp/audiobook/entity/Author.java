package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.audiobook.entity.template.Person;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
public class Author extends Person {

    @Column(columnDefinition = "TEXT")
    private String biography;

}
