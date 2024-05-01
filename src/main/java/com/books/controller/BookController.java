package com.books.controller;

import com.books.constants.Constants;
import com.books.dto.ResponseDto;
import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.dto.category.GetCategoryDto;
import com.books.entity.Book;
import com.books.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/books/")
public class BookController {

    private final BookService bookService;

    @GetMapping("getAll")
    public ResponseEntity<List<Book>> getAll(@RequestParam int page, @RequestParam int size) {
        List<Book> books = bookService.getAllBooks(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/getById/{book-id}")
    public ResponseEntity<Optional<GetBookDto>> getById(@Valid @PathVariable("book-id") Integer bookId) {
        Optional<GetBookDto> bookDto = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody CreateBookDto bookDto) {
        bookService.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody UpdateBookDto bookDto) {
        bookService.updateBook(bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
    }


    @DeleteMapping("delete/{book-id}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable("book-id") Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
    }
}

