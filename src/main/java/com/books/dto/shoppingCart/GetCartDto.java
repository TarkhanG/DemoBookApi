package com.books.dto.shoppingCart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartDto {
    @NotNull(message = "Book ID must be provided")
    private Integer bookId;

    @NotBlank(message = "Book name cannot be blank")
    private String bookName;

    @NotBlank(message = "Author name cannot be blank")
    private String author;

    @NotNull(message = "Quantity must be provided")
    private int quantity;

    @NotNull(message = "Price must be provided")
    private Double price;
    private Double totalPrice;
}
