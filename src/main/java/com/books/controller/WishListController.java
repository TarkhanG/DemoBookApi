package com.books.controller;

import com.books.constants.Constants;
import com.books.dto.ResponseDto;
import com.books.dto.wishlist.AddWishListDto;
import com.books.dto.wishlist.GetWishListDto;
import com.books.service.WishListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
@Validated
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addToWishList(
            @Valid
            @RequestBody AddWishListDto addWishListDto
    ) {
        wishListService.addToWishList(addWishListDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(
                        Constants.STATUS_200,
                        Constants.MESSAGE_WISHLIST_ADDED)
        );
    }

    @DeleteMapping("/deleteWishList/{wishlist-id}")
    public ResponseEntity<ResponseDto> deleteWishList(
            @Valid @PathVariable("wishlist-id") Integer wishListId
    ) {
        wishListService.deleteWishList(wishListId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(
                        Constants.STATUS_200,
                        Constants.MESSAGE_200)
        );
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<GetWishListDto>> getUserWishList(
            @Valid
            @PathVariable Integer userId
    ) {
        List<GetWishListDto> wishlistDtos = wishListService.getUserWishList(userId);
        return ResponseEntity.ok(wishlistDtos);
    }
}
