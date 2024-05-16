package com.books.service.impl;

import com.books.dto.wishlist.AddWishListDto;
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

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public AddWishListDto addToWishList(AddWishListDto addWishListDto) {
        AppUser user = userRepository.findById(addWishListDto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User" ,"User ID" ,addWishListDto.getUserId())
                );

        Book book = bookRepository.findById(addWishListDto.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book" ,"Book ID" ,addWishListDto.getBookId())
                );

        WishList existingWishList = wishListRepository.findByUserAndBook(user, book);
        if (existingWishList != null) {
            throw new BookAPIException("Book already added: " + addWishListDto.getBookId());
        }

        WishList newWishList = new WishList();
        newWishList.setUser(user);
        newWishList.setBook(book);

        wishListRepository.save(newWishList);
        return modelMapper.map(newWishList , AddWishListDto.class);
    }


    @Override
    public void deleteWishList(Integer id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Wish List", "Wish List ID", id));
        wishListRepository.delete(wishList);
    }

    @Override
    public List<GetWishListDto> getUserWishList(Integer userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "User ID", userId));

        List<WishList> wishLists = wishListRepository.findByUser(user);

        List<GetWishListDto> wishlistDtos = new ArrayList<>();
        for (WishList wishList : wishLists) {
            Book book = wishList.getBook();
            GetWishListDto dto = modelMapper.map(book, GetWishListDto.class);
            dto.setBookId(wishList.getBook().getBookId());
            wishlistDtos.add(dto);
        }

        return wishlistDtos;
    }

}
