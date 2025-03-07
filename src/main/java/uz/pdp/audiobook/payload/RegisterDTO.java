package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegisterDTO {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character (@#$%^&+=!)"
    )
    private String password;

    @NotBlank(message = "First name must not be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth must not be null")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

}
