package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class UserPreferencesDTO {

    @NotEmpty(message = "You must select at least one category")
    @Size(min = 3, message = "You must choose at least three categories")
    private Set<Integer> categoryIds;
}
