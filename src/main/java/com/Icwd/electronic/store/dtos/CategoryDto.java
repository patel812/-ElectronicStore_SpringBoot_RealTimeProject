package com.Icwd.electronic.store.dtos;

import com.Icwd.electronic.store.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString

public class CategoryDto extends Category {

    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title must be of minimum 4 characters !!")
    private String title;

    @NotBlank(message = "Description required !!")
    private String description;

    @NotBlank
    private String coverImage;

}
