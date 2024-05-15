package com.books.service.impl;

import com.books.dto.wishlist.AddWishListDto;
import com.books.entity.AppUser;
import com.books.entity.Book;
import com.books.entity.WishList;
import com.books.exception.ResourceNotFoundException;
import com.books.repository.book.BookRepository;
import com.books.repository.user.UserRepository;
import com.books.repository.wishlist.WishListRepository;
import com.books.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    @Override
    public WishList addWishList(AddWishListDto addWishListDto) {
        Optional<Book> optionalBook = bookRepository.findById(addWishListDto.getBookId());
        Optional<AppUser> optionalAppUser = userRepository.findById(addWishListDto.getUserId());

        if (optionalBook.isPresent() && optionalAppUser.isPresent()) {
            WishList wishList = new WishList();

            wishList.setBook(optionalBook.get());
            wishList.setUser(optionalAppUser.get());
            return wishListRepository.save(wishList);
        } else {
            throw new ResourceNotFoundException("Book or user not found");
        }
    }

//    @Override
//    public List<WishList> getUserWishList(Integer userId) {
//        List<WishList> userWishLists = wishListRepository.finByUserId(userId);
//
//        if (userWishLists.isEmpty()) {
//            throw new ResourceNotFoundException("User's wish list not found with ID: " + userId);
//        }
//
//        return userWishLists;
//    }


    @Override
    public void deleteWishList(Integer id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Wish List not found with ID: " + id));
        wishListRepository.delete(wishList);
    }
}
