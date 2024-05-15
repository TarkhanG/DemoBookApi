package com.books.dto.wishlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWishListDto {
    private Integer bookId;
    private Integer userId;
}
