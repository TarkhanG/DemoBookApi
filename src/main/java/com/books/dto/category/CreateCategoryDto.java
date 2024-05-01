package com.books.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {

    @NotBlank(message = "Category name can not be a null or empty")
    private String categoryName;
    @NotBlank(message = "Category description can not be a null or empty")
    private String description;
}
