package com.books.repository.shoppingCart;

import com.books.entity.AppUser;
import com.books.entity.Book;
import com.books.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findByUserId(Integer userId);
    ShoppingCart findByUserAndBook(AppUser user, Book book);
    List<ShoppingCart> findByUser(AppUser user);
}
