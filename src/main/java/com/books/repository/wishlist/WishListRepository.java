package com.books.repository.wishlist;

import com.books.entity.AppUser;
import com.books.entity.Book;
import com.books.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    WishList findByUserAndBook(AppUser user, Book book);
    List<WishList> findByUser(AppUser user);
}
