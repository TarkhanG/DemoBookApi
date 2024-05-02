package com.books.service.impl;

import com.books.dto.book.GetBookDto;
import com.books.dto.category.CreateCategoryDto;
import com.books.dto.category.GetCategoryByBooks;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.entity.Category;
import com.books.exception.ResourceNotFoundException;
import com.books.repository.category.CategoryRepository;
import com.books.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateCategoryDto addCategory(CreateCategoryDto categoryDto) {
        Category cat = modelMapper.map(categoryDto, Category.class);
        Category addedCat = categoryRepository.save(cat);
        return modelMapper.map(addedCat, CreateCategoryDto.class);
    }

    @Override
    public Page<GetCategoryDto> getAllCategories(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(cat -> modelMapper.map(cat, GetCategoryDto.class));
    }

    @Override
    public UpdateCategoryDto updateCategory(UpdateCategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Category id ", categoryId));
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, UpdateCategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        categoryRepository.delete(category);
    }

    @Override
    public GetCategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        return modelMapper.map(category, GetCategoryDto.class);
    }

    @Override
    public GetCategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByCategoryName(name);
        return modelMapper.map(category, GetCategoryDto.class);
    }

    public List<GetCategoryDto> getCategoriesStartingWith(String prefix) {
        List<Category> categories = categoryRepository.findAll();
        List<GetCategoryDto> result = categories.stream()
                .filter(cat -> cat.getCategoryName().toLowerCase().startsWith(prefix.toLowerCase()))
                .map(cat -> modelMapper.map(cat, GetCategoryDto.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public GetCategoryByBooks getBooksByCategoryName(String categoryName) {
        Category category = categoryRepository.findCategoryByCategoryName(categoryName);
        if (category == null) {
            throw new ResourceNotFoundException("Category", "Category name", categoryName);
        }

        GetCategoryByBooks result = modelMapper.map(category, GetCategoryByBooks.class);
        List<GetBookDto> bookDtos = category.getBooks().stream()
                .map(book -> modelMapper.map(book, GetBookDto.class))
                .collect(Collectors.toList());
        result.setBooks(bookDtos);

        return result;
    }

}