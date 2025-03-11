package uz.pdp.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Review extends AbsIntegerEntity {

    @ManyToOne
    private User user; // Foydalanuvchi bilan bog‘lash

    @ManyToOne
    private Audiobook audiobook; // Audiokitob bilan bog‘lash

    @NotBlank
    @Column(nullable = false, length = 1000)
    private String content; // Review matni (bo‘sh bo‘lmasligi kerak)

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating; // Rating (1 dan 5 gacha)
}
