package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Author extends Person {


    @Column(columnDefinition = "TEXT")
    private String biography;

}
