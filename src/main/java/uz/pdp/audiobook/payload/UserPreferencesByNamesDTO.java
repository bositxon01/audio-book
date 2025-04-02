package uz.pdp.audiobook.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPreferencesByNamesDTO {
    @NotEmpty(message = "You must select at least three categories")
    @Size(min = 3, message = "You must choose at least three categories")
    private Set<String> preferredCategories;
}
