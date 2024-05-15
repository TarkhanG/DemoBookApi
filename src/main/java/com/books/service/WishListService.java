package com.books.service;

import com.books.dto.wishlist.AddWishListDto;
import com.books.entity.WishList;

import java.util.List;

public interface WishListService {
    WishList addWishList(AddWishListDto addWishListDto);
//    List<WishList> getUserWishList(Integer userId);
    void deleteWishList(Integer id);
}
