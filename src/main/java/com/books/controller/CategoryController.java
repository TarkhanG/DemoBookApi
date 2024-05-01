package com.books.controller;

import com.books.constants.Constants;
import com.books.dto.category.CreateCategoryDto;
import com.books.dto.ResponseDto;
import com.books.dto.category.GetCategoryDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.entity.Category;
import com.books.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/category/")
@AllArgsConstructor
@Validated
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAll(@RequestParam int page, @RequestParam int size) {
        List<Category> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }


    @GetMapping("/getById/{category-id}")
    public ResponseEntity<Optional<GetCategoryDto>> getById(@Valid @PathVariable("category-id") Integer categoryId) {
        Optional<GetCategoryDto> categoryDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody CreateCategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody UpdateCategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
    }

    @DeleteMapping("/delete/{category-id}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable("category-id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
    }
}
