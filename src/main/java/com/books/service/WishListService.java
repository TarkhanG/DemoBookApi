package com.books.service;

import com.books.dto.wishlist.AddWishListDto;
import com.books.dto.wishlist.GetWishListDto;

import java.util.List;


public interface WishListService {
    AddWishListDto addToWishList(AddWishListDto addWishListDto);
    void deleteWishList(Integer id);
    List<GetWishListDto> getUserWishList(Integer userId);
}
