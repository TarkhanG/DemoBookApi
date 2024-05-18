package com.books.service.impl;

import com.books.dto.auth.JWTDto;
import com.books.dto.shoppingCart.AddToCartDto;
import com.books.dto.shoppingCart.GetCartDto;
import com.books.dto.wishlist.GetWishListDto;
import com.books.entity.AppUser;
import com.books.entity.Book;
import com.books.entity.ShoppingCart;
import com.books.entity.WishList;
import com.books.exception.BookAPIException;
import com.books.exception.ResourceNotFoundException;
import com.books.repository.book.BookRepository;
import com.books.repository.shoppingCart.ShoppingCartRepository;
import com.books.repository.user.UserRepository;
import com.books.service.ShoppingCartService;
import com.books.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;


    @Override
    public ShoppingCart addItemToCart(AddToCartDto cartDto, String token) {
        try {
            JWTDto jwtDto = jwtUtil.decodeToken(token);
            Integer userId = jwtDto.getUserId();

            AppUser user = userRepository.findById(userId).orElseThrow(() ->
                    new ResourceNotFoundException("User", "ID", userId));

            Book book = bookRepository.findById(cartDto.getBookId()).orElseThrow(() ->
                    new ResourceNotFoundException("Book", "ID", cartDto.getBookId()));

            if (book.getQuantity() < cartDto.getQuantity()) {
                throw new BookAPIException("Insufficient stock for book with ID: " + cartDto.getBookId());
            }

            ShoppingCart cart = shoppingCartRepository.findByUserAndBook(user, book);
            if (cart != null) {
                cart.setQuantity(cart.getQuantity() + cartDto.getQuantity());
            } else {
                cart = new ShoppingCart();
                cart.setUser(user);
                cart.setBook(book);
                cart.setQuantity(cartDto.getQuantity());
            }

//            book.setQuantity(book.getQuantity() - cartDto.getQuantity());
//            bookRepository.save(book);

            shoppingCartRepository.save(cart);
            return cart;
        } catch (Exception e) {
            throw new BookAPIException("Error adding item to cart: " + e.getMessage());
        }
    }

    @Override
    public List<GetCartDto> getUserCart(String token) {
        try {
            JWTDto jwtDto = jwtUtil.decodeToken(token);
            Integer userId = jwtDto.getUserId();

            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User", "User ID", userId));

            List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user);

            if (shoppingCarts.isEmpty()) {
                return new ArrayList<>();
            }

            List<GetCartDto> cartDtos = new ArrayList<>();
            for (ShoppingCart shoppingCart : shoppingCarts) {
                Book book = shoppingCart.getBook();

                GetCartDto dto = new GetCartDto();
                dto.setBookId(book.getBookId());
                dto.setBookName(book.getBookName());
                dto.setAuthor(book.getAuthor());
                dto.setQuantity(shoppingCart.getQuantity());
                dto.setPrice(book.getPrice());
                BigDecimal totalPrice = BigDecimal.valueOf(book.getPrice() * shoppingCart.getQuantity())
                        .setScale(2, RoundingMode.HALF_UP);
                dto.setTotalPrice(totalPrice.doubleValue());

                cartDtos.add(dto);
            }
            return cartDtos;

        } catch (Exception e) {
            throw new BookAPIException("Error retrieving cart: " + e.getMessage());
        }
    }

    @Override
    public void removeItemFromCart(Integer bookId, String token) {
        try {
            JWTDto jwtDto = jwtUtil.decodeToken(token);
            Integer userId = jwtDto.getUserId();

            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User", "User ID", userId));

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Book", "Book ID", bookId));

            ShoppingCart cart = shoppingCartRepository.findByUserAndBook(user, book);
            if (cart != null) {
                shoppingCartRepository.delete(cart);
            } else {
                throw new ResourceNotFoundException("Shopping Cart", "Book ID", bookId);
            }

        } catch (Exception e) {
            throw new BookAPIException("Error removing item from cart: " + e.getMessage());
        }
    }


}
