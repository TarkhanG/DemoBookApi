package com.books.service.impl;

import com.books.dto.auth.JWTDto;
import com.books.dto.wishlist.GetWishListDto;
import com.books.entity.AppUser;
import com.books.entity.Book;
import com.books.entity.WishList;
import com.books.exception.BookAPIException;
import com.books.exception.ResourceNotFoundException;
import com.books.repository.book.BookRepository;
import com.books.repository.user.UserRepository;
import com.books.repository.wishlist.WishListRepository;
import com.books.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import com.books.util.JWTUtil;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;


    @Override
    public WishList addToWishList(String token, Integer bookId) {

        try{
            JWTDto jwtDto = jwtUtil.decodeToken(token);
            Integer userId = jwtDto.getUserId();

            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User" ,"User ID" , userId)
                    );

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Book" ,"Book ID" , bookId)
                    );

            WishList existingWishList = wishListRepository.findByUserAndBook(user, book);
            if (existingWishList != null) {
                throw new BookAPIException("Book already added: " + bookId);
            }

            WishList newWishList = new WishList();
            newWishList.setUser(user);
            newWishList.setBook(book);

            return wishListRepository.save(newWishList);
        }catch (Exception e) {
            throw new BookAPIException("Error retrieving wishlist: " + e.getMessage());
        }

    }


    @Override
    public void deleteWishList(Integer id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Wish List", "Wish List ID", id));
        wishListRepository.delete(wishList);
    }

    @Override
    public List<GetWishListDto> getUserWishList(String token) {
        try {
            JWTDto jwtDto = jwtUtil.decodeToken(token);
            Integer userId = jwtDto.getUserId();

            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User", "User ID", userId));

            List<WishList> wishLists = wishListRepository.findByUser(user);

            if (wishLists.isEmpty()) {
                return new ArrayList<>();
            }

            List<GetWishListDto> wishlistDtos = new ArrayList<>();
            for (WishList wishList : wishLists) {
                Book book = wishList.getBook();

                GetWishListDto dto = modelMapper.map(book, GetWishListDto.class);
                dto.setBookId(book.getBookId());

                wishlistDtos.add(dto);
            }
            return wishlistDtos;

        } catch (Exception e) {
            throw new BookAPIException("Error retrieving wishlist: " + e.getMessage());
        }
    }

}
