package com.books.service;

import com.books.dto.category.CreateCategoryDto;
import com.books.dto.category.GetCategoryByBooks;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CategoryService{
 CreateCategoryDto addCategory(CreateCategoryDto categoryDto);
 Page<GetCategoryDto> getAllCategories (int page, int pageSize);
 UpdateCategoryDto updateCategory (UpdateCategoryDto categoryDto, Integer categoryId);
 void deleteCategory (Integer categoryId);
 GetCategoryDto getCategory (Integer categoryId);
 GetCategoryDto getCategoryByName (String name);
 List<GetCategoryDto> getCategoriesStartingWith(String prefix);
 GetCategoryByBooks getBooksByCategoryName(String categoryName);
}
