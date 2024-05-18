package com.books.controller;

import com.books.constants.Constants;
import com.books.dto.ResponseDto;
import com.books.dto.shoppingCart.AddToCartDto;
import com.books.dto.shoppingCart.GetCartDto;
import com.books.dto.wishlist.GetWishListDto;
import com.books.entity.ShoppingCart;
import com.books.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Validated
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> addItemToCart(
            @RequestHeader("Authorization") String token,
            @RequestBody AddToCartDto cartDto) {

        ShoppingCart cart = shoppingCartService.addItemToCart(cartDto, token);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/getByUserId")
    public ResponseEntity<List<GetCartDto>> getUserCart(
            @RequestHeader("Authorization") String authHeader
    ) {
        List<GetCartDto> cartDtos = shoppingCartService.getUserCart(authHeader);
        return ResponseEntity.ok(cartDtos);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDto> deleteCart(
            @RequestHeader("Authorization") String token,
            @Valid @PathVariable("bookId") Integer bookId
    ){
        shoppingCartService.removeItemFromCart(bookId, token);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(
                        Constants.STATUS_200,
                        Constants.MESSAGE_200)
        );
    }

}
