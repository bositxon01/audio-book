package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Audiobook extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    @Column(nullable = false)
    private Integer duration;

    @URL
    private String coverUrl;

}
