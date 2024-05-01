package com.books.service.impl;

import com.books.dto.category.CreateCategoryDto;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.entity.Category;
import com.books.exception.ResourceNotFoundException;
import com.books.mapper.CategoryMapper;
import com.books.repository.category.CategoryRepository;
import com.books.repository.category.CategorySortingRepository;
import com.books.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorySortingRepository categorySortingRepository;


    @Override
    public Category createCategory(CreateCategoryDto categoryDto) {
        Category category = CategoryMapper.mapCreateCategoryDtoToCategory(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public UpdateCategoryDto updateCategory(UpdateCategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(categoryDto.getCategoryId());
        if (category.isPresent()) {
            Category result = CategoryMapper.mapUpdateCategoryDtoToCategory(categoryDto);
            categoryRepository.save(result);
            return CategoryMapper.mapCategoryToUpdateCategoryDto(result);
        }
         throw new ResourceNotFoundException("Category", "ID", String.valueOf(categoryDto.getCategoryId()));
    }

    @Override
    public Optional<Category> deleteCategory(Integer id) {
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()) {
            categoryRepository.delete(result.get());
            return result;
        } else {
            throw new ResourceNotFoundException("Category", "ID", String.valueOf(id));
        }
    }


    @Override
    public List<Category> getAllCategories(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Category> categoryPage = categorySortingRepository.findAll(pageable);
        return categoryPage.toList();
    }

    @Override
    public Optional<GetCategoryDto> getCategoryById(Integer id) {
        if (id != null &&  categoryRepository.findById(id).isPresent()){
            Optional<GetCategoryDto> result = categoryRepository.findById(id)
                    .map(category -> CategoryMapper.mapCategorytoGetCategoryDto(category));
            return result;
        }
        throw new ResourceNotFoundException("Category", "ID" ,String.valueOf(id));
    }
}
