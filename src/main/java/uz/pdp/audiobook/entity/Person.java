package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
public abstract class Person extends AbsIntegerEntity {

    @NotBlank(message = "Firstname cannot be blank")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Lastname cannot be blank")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfBirth;
}
