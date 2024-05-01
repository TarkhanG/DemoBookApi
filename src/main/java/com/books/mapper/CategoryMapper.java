package com.books.mapper;

import com.books.dto.category.CreateCategoryDto;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.entity.Category;

public class CategoryMapper {

    //TODO Create Category Dto
    public static Category mapCreateCategoryDtoToCategory(CreateCategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    //TODO Update Category Dto
    public static UpdateCategoryDto mapCategoryToUpdateCategoryDto(Category category) {
        UpdateCategoryDto dto = new UpdateCategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public static Category mapUpdateCategoryDtoToCategory(UpdateCategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    //TODO Get Category Dto
    public static GetCategoryDto mapCategorytoGetCategoryDto(Category category) {
        GetCategoryDto dto = new GetCategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }

}
