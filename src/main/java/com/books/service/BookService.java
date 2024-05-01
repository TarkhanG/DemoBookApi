package com.books.service;

import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(CreateBookDto dto);
    List<Book> getAllBooks(int page, int pageSize);
    Optional<GetBookDto> getBookById(Integer id);
    UpdateBookDto updateBook(UpdateBookDto dto);
    Book deleteBook(Integer id);
}
