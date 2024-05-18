package com.books.service;


import com.books.dto.shoppingCart.AddToCartDto;
import com.books.dto.shoppingCart.GetCartDto;
import com.books.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(AddToCartDto cartDto, String token);
    List<GetCartDto> getUserCart(String token);
    void removeItemFromCart(Integer bookId, String token);

}
