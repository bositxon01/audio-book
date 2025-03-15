package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.pdp.audiobook.entity.template.AbsIntegerEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "audiobook"})
@Entity
public class Review extends AbsIntegerEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "audiobook_id", nullable = false)
    private Audiobook audiobook;

    @NotBlank
    @Column(nullable = false, length = 1000)
    private String content;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Integer rating;
}
