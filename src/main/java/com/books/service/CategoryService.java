package com.books.service;

import com.books.dto.category.CreateCategoryDto;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService{
 Category createCategory(CreateCategoryDto categoryDto);
 UpdateCategoryDto updateCategory(UpdateCategoryDto categoryDto);
 Optional<Category> deleteCategory(Integer id);
 List<Category> getAllCategories(int page, int pageSize);
 Optional<GetCategoryDto> getCategoryById(Integer id);
}
