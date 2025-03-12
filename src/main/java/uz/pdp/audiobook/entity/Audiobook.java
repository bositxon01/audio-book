package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Audiobook extends AbsIntegerEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Author author;

    @Min(1)
    @Column(nullable = false)
    private Integer durationMinutes;

    @URL
    private String coverUrl;

}
