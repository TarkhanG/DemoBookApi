package com.books.controller;

import com.books.constants.Constants;
import com.books.dto.category.CreateCategoryDto;
import com.books.dto.ResponseDto;
import com.books.dto.category.GetCategoryByBooks;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category/")
@AllArgsConstructor
@Validated
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/get/{category-id}")
    public ResponseEntity<GetCategoryDto> getCategory(@PathVariable("category-id") Integer id) {
        GetCategoryDto category = categoryService.getCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping("/getByName/{category-name}")
    public ResponseEntity<GetCategoryDto> getCategoryByName(
            @Valid @PathVariable("category-name") String categoryName
    ) {
        GetCategoryDto result = categoryService.getCategoryByName(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/searchByCategoryName/{category-name}")
    public ResponseEntity<List<GetCategoryDto>> getAllCategoriesByName(
            @Valid @RequestParam("category-name") String categoryName
    ) {
        List<GetCategoryDto> categories = categoryService.getCategoriesStartingWith(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("getCategoryByBooks/{category-name}")
    public ResponseEntity<GetCategoryByBooks> getCategoryByBooks(
            @PathVariable("category-name") String categoryName
    ) {
        GetCategoryByBooks categoryByBooks = categoryService.getBooksByCategoryName(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(categoryByBooks);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<GetCategoryDto>> getAll(
            @RequestParam int page, @RequestParam int size
    ) {
        Page<GetCategoryDto> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDto> create(
            @Valid @RequestBody CreateCategoryDto categoryDto
    ) {
        categoryService.addCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(Constants.STATUS_201,
                        Constants.MESSAGE_201)
        );
    }

    @PutMapping("/update/{category-id}")
    public ResponseEntity<ResponseDto> updateCategory
            (@Valid @RequestBody UpdateCategoryDto categoryDto,
             @Valid @PathVariable("category-id") Integer id
            ) {
        categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(
                        Constants.STATUS_200,
                        Constants.MESSAGE_200)
        );

    }

    @DeleteMapping("/delete/{category-id}")
    public ResponseEntity<ResponseDto> delete(
            @Valid @PathVariable("category-id") Integer categoryId
    ) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(Constants.STATUS_200,
                        Constants.MESSAGE_200)
        );
    }
}