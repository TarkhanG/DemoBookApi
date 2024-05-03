package com.books.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDto {

    @NotBlank(message = "Category name can not be a null or empty")
    private String categoryName;
    @NotBlank(message = "Category description can not be a null or empty")
    private String description;
}
