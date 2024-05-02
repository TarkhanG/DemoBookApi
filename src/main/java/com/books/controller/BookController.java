package com.books.controller;

import com.books.constants.Constants;
import com.books.dto.ResponseDto;
import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookByCategory;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/books/")
public class BookController {

    private final BookService bookService;

    @GetMapping("getAll")
    public ResponseEntity<Page<GetBookDto>> getAllBooks(int size, int pageSize) {
        Page<GetBookDto> result = bookService.getAllBooks(size, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getById/{book-id}")
    public ResponseEntity<GetBookDto> getById(@Valid @PathVariable("book-id") Integer bookId) {
        GetBookDto bookDto = bookService.getBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @GetMapping("getBookByBookName/{book-name}")
    public ResponseEntity<GetBookDto> getBookByBookName(@PathVariable("book-name") String bookName) {
        GetBookDto result = bookService.findBookByBookName(bookName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("getBookByCategory/{book-id}")
    public ResponseEntity<GetBookByCategory> getBookByCategory(@PathVariable("book-id") Integer bookId) {
        GetBookByCategory result = bookService.getBookByCategory(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDto> createBook(@Valid @RequestBody CreateBookDto createBookDto) {
        bookService.addBook(createBookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody UpdateBookDto bookDto, @RequestParam Integer id) {
        bookService.updateBook(bookDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
    }

    @DeleteMapping("delete/{book-id}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable("book-id") Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200));
    }
}

