package com.books.service;

import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookByCategory;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface BookService {
//    Optional<GetBookDto> getBookById(Integer id);


    CreateBookDto addBook(CreateBookDto createBookDto);
    Page<GetBookDto> getAllBooks(int page, int pageSize);
    UpdateBookDto updateBook(UpdateBookDto updateBookDto, Integer id);
    void deleteBook(Integer id);
    GetBookDto getBook(Integer id);
    GetBookDto findBookByBookName(String bookName);
    GetBookByCategory getBookByCategory(Integer categoryId);
}
