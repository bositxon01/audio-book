package uz.pdp.audiobook.payload;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegisterDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

}
