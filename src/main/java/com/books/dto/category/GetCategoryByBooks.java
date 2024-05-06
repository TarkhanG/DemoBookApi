package com.books.dto.category;

import com.books.dto.book.GetBookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryByBooks {
    private Integer categoryId;
    private String categoryName;
    private String description;
    private List<GetBookByCategoryDto> books;
}
