package uz.pdp.audiobook.payload;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class CategoryDTO {

    private Integer id;
    private String name;
}