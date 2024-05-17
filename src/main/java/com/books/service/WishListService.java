package com.books.service;

import com.books.dto.wishlist.GetWishListDto;
import com.books.entity.WishList;

import java.util.List;


public interface WishListService {
    WishList addToWishList(String token, Integer bookId);
    void deleteWishList(Integer id);
    List<GetWishListDto> getUserWishList(String token);
}
