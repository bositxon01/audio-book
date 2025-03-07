package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String username;   // You can rename this to 'email' if preferred

    @NotBlank(message = "Password must not be blank")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character (@#$%^&+=!)"
    )
    private String password;

}
