package uz.pdp.audiobook.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

    private Integer id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

}