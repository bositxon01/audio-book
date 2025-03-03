package uz.pdp.audiobook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class Person extends AbsIntegerEntity {

    @NotBlank(message = "Firstname cannot be blank")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Lastname cannot be blank")
    @Column(nullable = false)
    private String lastName;

    @Past
    @Column(nullable = false)
    private Date dateOfBirth;

}