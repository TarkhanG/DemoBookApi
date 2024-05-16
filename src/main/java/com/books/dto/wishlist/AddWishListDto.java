package com.books.dto.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWishListDto {

    @NotNull(message = "Book ID must be provided")
    private Integer bookId;
    @NotNull(message = "User ID must be provided")
    private Integer userId;
}
