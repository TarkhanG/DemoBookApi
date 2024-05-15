package com.books.dto.wishlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WishListDto {

    @NotNull(message = "Wish List ID must be provided")
    private Integer wishListId;

    @NotNull(message = "User ID must be provided")
    private Integer userId;

    @NotNull(message = "Book ID must be provided")
    private Integer bookId;

    @NotBlank(message = "Book name cannot be blank")
    private String bookName;

    @NotBlank(message = "Author name cannot be blank")
    private String author;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Publisher cannot be blank")
    private String publisher;

    @NotBlank(message = "Language cannot be blank")
    private String language;

    @NotBlank(message = "Cover description cannot be blank")
    private String cover;

    @NotNull(message = "Published year must be provided")
    private int publishedYear;

    @NotNull(message = "Quantity must be provided")
    private int quantity;

}
